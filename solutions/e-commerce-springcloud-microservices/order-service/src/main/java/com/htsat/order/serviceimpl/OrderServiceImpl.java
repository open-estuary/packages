package com.htsat.order.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.htsat.order.config.RedisConfig;
import com.htsat.order.dao.*;
import com.htsat.order.dto.*;
import com.htsat.order.exception.DeleteException;
import com.htsat.order.exception.InsertException;
import com.htsat.order.exception.SearchException;
import com.htsat.order.exception.UpdateException;
import com.htsat.order.model.*;
import com.htsat.order.service.IOrderService;
import com.htsat.order.utils.ComputeUtils;
import com.htsat.order.utils.ConvertToDTO;
import com.htsat.order.utils.SerializeUtil;
import com.htsat.order.utils.SortList;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class OrderServiceImpl implements IOrderService {

    private Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Autowired
    REcDeliveryinfoMapper deliveryinfoMapper;

    @Autowired
    REcOrderinfoMapper orderinfoMapper;

    @Autowired
    REcOrderskuMapper orderskuMapper;

    @Autowired
    REcSkuMapper skuMapper;

    @Autowired
    REcUserdeliveryaddressMapper addressMapper;

    @Autowired
    REcUserinfoMapper userinfoMapper;

    @Autowired
    REcUserbankinfoMapper userbankinfoMapper;

    @Autowired
    private RedisConfig redisConfig;

    private static Lock lock = new ReentrantLock();

    /**
     * create order
     */
    @Override
    public void createOrderAndDeliveryAndOrderSKU(OrderDTO orderDTO) throws InsertException {
        OrderDTO returnOrderDTO = createOrderAndDeliveryAndOrderSKUByMySQL(orderDTO);
        createOrderAndDeliveryAndOrderSKUToRedis(returnOrderDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=Exception.class)
    public OrderDTO createOrderAndDeliveryAndOrderSKUByMySQL(OrderDTO orderDTO) throws InsertException {
        REcDeliveryinfo deliveryinfo = createDeliveryInfo(orderDTO);
        REcOrderinfo orderinfo = createOrder(orderDTO, deliveryinfo);
        List<REcOrdersku> orderskuList = createOrderSKU(orderDTO,orderinfo);
        REcUserdeliveryaddress address = addressMapper.selectByPrimaryKey(orderDTO.getAddressDTO().getNaddressid());
        return ConvertToDTO.convertToOrderDTO(deliveryinfo, orderinfo, orderskuList, address);
    }

    private REcDeliveryinfo createDeliveryInfo(OrderDTO orderDTO) throws InsertException{
        Date timestamp = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String timeStr = df.format(timestamp);

        DeliveryDTO deliveryDTO = orderDTO.getDeliveryDTO();
        if (deliveryDTO == null)
            return null;
        REcDeliveryinfo deliveryinfo = new REcDeliveryinfo();
        deliveryinfo.setSexpresscompany(deliveryDTO.getSexpresscompany());
        deliveryinfo.setNaddressid(deliveryDTO.getNaddressid());
        deliveryinfo.setSconsignee(deliveryDTO.getSconsignee());
//        deliveryinfo.setSdeliverycode(deliveryDTO.getSdeliverycode());
        deliveryinfo.setSdeliverycode(orderDTO.getUserId() + "-delivery-" + timeStr);
        deliveryinfo.setSdeliverycomment(deliveryDTO.getSdeliverycomment());
        deliveryinfo.setNdeliveryprice(deliveryDTO.getNdeliveryprice());
        deliveryinfo.setCstatus((short) 0);//表示未揽件

        int result = deliveryinfoMapper.insertSelective(deliveryinfo);
        if (result != 1) {
            throw new InsertException("mysql : create  deliveryInfo failed!");
        }
        return deliveryinfo;
    }

    private REcOrderinfo createOrder(OrderDTO orderDTO, REcDeliveryinfo deliveryinfo) throws InsertException{
        REcOrderinfo orderinfo = new REcOrderinfo();
        Date timestamp = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String timeStr = df.format(timestamp);
        String orderCode = orderDTO.getUserId() + "-order-" + timeStr;

        orderinfo.setNaddressid(orderDTO.getAddressDTO().getNaddressid());
        orderinfo.setNdeliveryid(deliveryinfo.getNdeliveryid());
        orderinfo.setNtotalprice(orderDTO.getTotalPrice());
        orderinfo.setNtotalquantity(orderDTO.getTotalQuantity());
        orderinfo.setNuserid(orderDTO.getUserId());
        orderinfo.setSordercode(orderCode);
        orderinfo.setScustomermark(orderDTO.getScustomermark());
        orderinfo.setSordersource(orderDTO.getSordersource());
        orderinfo.setSordertype(orderDTO.getSordertype());
        orderinfo.setSparentorderid(orderDTO.getParentOrderid());
        orderinfo.setCpaymentmethod(orderDTO.getPaymentMethod());

        if (orderinfo.getDpaymenttime() == null) {
            orderinfo.setCstatus((short) 0);
        } else {
            orderinfo.setDpaymenttime(orderDTO.getDpaymenttime());
            orderinfo.setCstatus((short) 1);
        }

        //compute and get result
        orderinfo.setNdiscount(ComputeUtils.computeDiscount(orderDTO.getOrderskudtoList()));
        orderinfo.setNtotalprice(ComputeUtils.computeTotalPrice(orderDTO.getOrderskudtoList(), orderDTO.getDeliveryDTO()));
        orderinfo.setNtotalquantity(ComputeUtils.computeNumber(orderDTO.getOrderskudtoList()));

        int result = orderinfoMapper.insertSelective(orderinfo);
        if (result != 1) {
            throw new InsertException("mysql : create order failed");
        }
        return orderinfo;
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=Exception.class)
    public List<REcOrdersku> createOrderSKU(OrderDTO orderDTO, REcOrderinfo orderinfo) throws InsertException{
        List<OrderSKUDTO> skudtoList = orderDTO.getOrderskudtoList();
        List<REcOrdersku> orderskuList = new ArrayList<>();
        for (OrderSKUDTO orderskuDTO : skudtoList) {
            REcOrdersku ordersku = new REcOrdersku();
            ordersku.setNskuid(orderskuDTO.getSkuId());
            ordersku.setNorderid(orderinfo.getNorderid());

            if (StringUtils.isNotEmpty(orderskuDTO.getCurrency()))
                ordersku.setScurrency(orderskuDTO.getCurrency());
            ordersku.setNdiscount(orderskuDTO.getDiscount());
            ordersku.setNquantity(orderskuDTO.getQuantity());
            ordersku.setNorigprice(orderskuDTO.getOriginPrice());
            ordersku.setNprice(orderskuDTO.getPrice());

            orderskuList.add(ordersku);
            int resultAdd = orderskuMapper.insertSelective(ordersku);
            if (resultAdd != 1) {
                throw new InsertException("mysql : create orderSKU failed");
            }

//            updateSKUInventory(orderskuDTO);
            skuMapper.updateInventory(orderskuDTO.getSkuId(), orderskuDTO.getQuantity());
//            lock.lock();
//            try {
//                REcSku sku = skuMapper.selectByPrimaryKey(orderskuDTO.getSkuId());
//                sku.setNinventory(sku.getNinventory() - orderskuDTO.getQuantity());
//                int resultUpdate = skuMapper.updateByPrimaryKeySelective(sku);
//                if (resultUpdate != 1) {
//                    throw new InsertException("mysql : update sku inventory failed");
//                }
//            }
//            finally {
//                lock.unlock();
//            }

//            synchronized(this) {
//                REcSku sku = skuMapper.selectByPrimaryKey(orderskuDTO.getSkuId());
//                sku.setNinventory(sku.getNinventory() - orderskuDTO.getQuantity());
//                int resultUpdate = skuMapper.updateByPrimaryKeySelective(sku);
//                if (resultUpdate != 1) {
//                    throw new InsertException("mysql : update sku inventory failed");
//                }
//            }
        }
        return orderskuList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE,timeout=60,rollbackFor=Exception.class)
    public void updateSKUInventory(OrderSKUDTO orderskuDTO) throws InsertException {
        synchronized(this) {
            REcSku sku = skuMapper.selectByPrimaryKey(orderskuDTO.getSkuId());
            sku.setNinventory(sku.getNinventory() - orderskuDTO.getQuantity());
            int resultUpdate = skuMapper.updateByPrimaryKeySelective(sku);
            if (resultUpdate != 1) {
                throw new InsertException("mysql : update sku inventory failed");
            }
        }
    }

    private void createOrderAndDeliveryAndOrderSKUToRedis(OrderDTO orderDTO) throws InsertException{
        Jedis jedis = redisConfig.getJedis();
        String code = null;
        String dtoJson = JSON.toJSONString(orderDTO);
        try {
            code = jedis.set((orderDTO.getOrderId() + ""), dtoJson);
        } catch (Exception e) {
            logger.error("Redis insert error: "+ e.getMessage() +" - " + orderDTO.getOrderId() + ", value:" + orderDTO);
        } finally{
            redisConfig.returnResource(jedis);
        }
        if (StringUtils.isEmpty(code)) {
            throw new InsertException("redis : create failed");
        }
    }

    /**
     * get order
     */
    @Override
    public OrderDTO getOrderAndDeliveryAndOrderSKUAndAddress(Long orderId) throws SearchException {
        OrderDTO orderDTO = getOrderInfoByRedis(orderId);
        if (orderDTO == null )  orderDTO = getOrderInfoByMySQL(orderId);
        return orderDTO;
    }

    private OrderDTO getOrderInfoByMySQL(Long orderId) throws SearchException {
        REcOrderinfo orderinfo = orderinfoMapper.selectByPrimaryKey(orderId);
        if (orderinfo == null){
            throw new SearchException("mysql : get orderInfo failed");
        } else {
            Long deliveryId = orderinfo.getNdeliveryid();
            REcDeliveryinfo deliveryinfo = deliveryinfoMapper.selectByPrimaryKey(deliveryId);
            if (deliveryinfo == null) {
               throw new SearchException("mysql : get deliveryInfo failed");
            }
            Long addressId = orderinfo.getNaddressid();
            REcUserdeliveryaddress address = addressMapper.selectByPrimaryKey(addressId);
            if (address == null) {
                throw new SearchException("mysql : get address failed");
            }
            List<REcOrdersku> orderskuList = orderskuMapper.selectByOrderId(orderId);
            if (orderskuList == null) {
                throw new SearchException("mysql : get orderSKUList failed");
            }
            return ConvertToDTO.convertToOrderDTO(deliveryinfo, orderinfo, orderskuList, address);
        }
    }

    private OrderDTO getOrderInfoByRedis(Long orderId) throws SearchException {
        Jedis jedis = redisConfig.getJedis();
        if(jedis == null || !jedis.exists(orderId + "")){
            return null;
        }

        String orderInfo = null;
        try {
            orderInfo = jedis.get(orderId + "");
        } catch (Exception e) {
            logger.error("Redis get error: "+ e.getMessage() +" - key : " + orderId);
        } finally {
            redisConfig.returnResource(jedis);
        }

        if (StringUtils.isEmpty(orderInfo)) {
            return null;
        } else {
            OrderDTO orderDTO = JSON.parseObject(orderInfo, OrderDTO.class);
            return orderDTO;
        }

    }

    /**
     * delete order
     */
    @Override
    public void deleteOrderAndDeliveryAndOrderSKU(Long orderId) throws DeleteException {
        deleteOrderInfoByMySQL(orderId);
        deleteOrderInfoByRedis(orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=Exception.class)
    public void deleteOrderInfoByMySQL(Long orderId) throws DeleteException {
        REcOrderinfo orderinfo = orderinfoMapper.selectByPrimaryKey(orderId);
        List<REcOrdersku> orderskuList = orderskuMapper.selectByOrderId(orderId);

        //add inventory of sku
        for (REcOrdersku ordersku : orderskuList) {
            REcSku sku = skuMapper.selectByPrimaryKey(ordersku.getNskuid());
            sku.setNinventory(sku.getNinventory() + ordersku.getNquantity());
            int resultInventory = skuMapper.updateByPrimaryKey(sku);
            if (resultInventory != 1) {
                throw new DeleteException("mysql : update SKU inventory failed");
            }
        }
        //delete delivery
        int resultDelivery = deliveryinfoMapper.deleteByPrimaryKey(orderinfo.getNdeliveryid());
        if (resultDelivery != 1) {
            throw new DeleteException("mysql : delete deliveryInfo failed");
        }
        //delete order
        int resultOrder = orderinfoMapper.deleteByPrimaryKey(orderId);
        if (resultOrder != 1) {
            throw new DeleteException("mysql : delete orderInfo failed");
        }
        //delete ordersku
        for (REcOrdersku ordersku : orderskuList) {
            REcOrderskuKey key = new REcOrderskuKey();
            key.setNorderid(orderId);
            key.setNskuid(ordersku.getNskuid());

            int resultOrderSKU = orderskuMapper.deleteByOrderskuKey(key);
            if (resultOrderSKU != 1) {
                throw new DeleteException("mysql : delete orderSKUList failed");
            }
        }
    }

    private void deleteOrderInfoByRedis(Long orderId) throws DeleteException {
        Jedis jedis = redisConfig.getJedis();
        try {
            Long reply = jedis.del(orderId + "");
        } catch (Exception e) {
            logger.error("Redis delete error: "+ e.getMessage() +" - key : " + orderId);
        }finally{
            redisConfig.returnResource(jedis);
        }
        if (false) {
            throw new DeleteException("redis : delete failed");
        }
    }

    /**
     * update order delivery
     * order  0：未支付;1：已支付未发货;2：已发货;3：已接收;4：已关闭
     * delivery  1：已收件;2：在途;3：待签收;4：已签收
     */
    @Override
    public OrderDTO updateOrderDelivery(Long orderId, short deliveryStatus) throws UpdateException {
        OrderDTO orderDTO = updateOrderDeliveryByMySQL(orderId, deliveryStatus);
        updateOrderDeliveryByRedis(orderDTO);
        return orderDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=Exception.class)
    public OrderDTO updateOrderDeliveryByMySQL(Long orderId, short deliveryStatus) throws UpdateException{
        REcOrderinfo orderinfo = orderinfoMapper.selectByPrimaryKey(orderId);
        if (orderinfo == null) {
            throw new UpdateException("mysql : update get orderInfo failed");
        }
        REcDeliveryinfo deliveryinfo = deliveryinfoMapper.selectByPrimaryKey(orderinfo.getNdeliveryid());
        if (deliveryinfo == null) {
            throw new UpdateException("mysql : update get deliveryInfo failed");
        }

        //update delivery
        deliveryinfo.setCstatus(deliveryStatus);
        int resultDelivery = deliveryinfoMapper.updateByPrimaryKey(deliveryinfo);
        if (resultDelivery != 1) {
            throw new UpdateException("mysql : update deliveryInfo status failed");
        }

        //update order
        if (deliveryStatus == (short) 4) {//delivery has sign , order receive
            orderinfo.setCstatus((short) 3);
        } else if (deliveryStatus == (short) 1
                    || deliveryStatus == (short) 2
                    || deliveryStatus == (short) 3){
            orderinfo.setCstatus((short) 2);
        } else {
            //do nothing
        }
        int resultOrder = orderinfoMapper.updateByPrimaryKey(orderinfo);
        if (resultOrder != 1) {
            throw new UpdateException("mysql : update orderInfo status failed");
        }

        REcUserdeliveryaddress address = addressMapper.selectByPrimaryKey(orderinfo.getNaddressid());
        if (address == null) {
            throw new UpdateException("mysql : update get address failed");
        }

        List<REcOrdersku> orderskuList = orderskuMapper.selectByOrderId(orderId);
        if (orderskuList == null) {
            throw new UpdateException("mysql : update get orderSKUList failed");
        }

        return ConvertToDTO.convertToOrderDTO(deliveryinfo, orderinfo, orderskuList, address);
    }

    private void updateOrderDeliveryByRedis(OrderDTO orderDTO) throws UpdateException{
        Jedis jedis = redisConfig.getJedis();
        String reply = null;
        String dtoJson = JSON.toJSONString(orderDTO);
        try {
            reply = jedis.set((orderDTO.getOrderId() + ""), dtoJson);
        } catch (Exception e) {
            logger.error("Redis update error: "+ e.getMessage() +" - " + orderDTO.getOrderId() + "" + ", value:" + orderDTO);
        }finally{
            redisConfig.returnResource(jedis);
        }

        if (StringUtils.isEmpty(reply)) {
            throw new UpdateException("redis : update failed");
        }
    }

    @Override
    public OrderDTO updateOrderPayment(Long userId, Long orderId, String cardId, String paymentPassword) throws UpdateException{
        OrderDTO orderDTO = updateOrderPaymentByMySQL(userId, orderId, cardId, paymentPassword);
        updateOrderPaymentByRedis(orderDTO);
        return orderDTO;
    }

    private OrderDTO updateOrderPaymentByMySQL(Long userId, Long orderId, String cardId, String paymentPassword) throws UpdateException{
        REcUserinfo userinfo = userinfoMapper.selectByPrimaryKey(userId);
        if (userinfo == null) {
            throw new UpdateException("mysql : update get userInfo failed");
        }
        REcUserbankinfo userbankinfo = userbankinfoMapper.selectByPrimaryKey(cardId);
        if (userbankinfo == null) {
            throw new UpdateException("mysql : update get userbankInfo failed");
        }
        REcOrderinfo orderinfo = orderinfoMapper.selectByPrimaryKey(orderId);
        if (orderinfo == null) {
            throw new UpdateException("mysql : update get orderInfo failed");
        }
        if (paymentPassword.equals(userinfo.getSpaypassword()) && userbankinfo != null && cardId.equals(userbankinfo.getScardid()) && orderinfo != null) {
            if (orderinfo.getCstatus() == (short) 0){
                orderinfo.setCstatus((short) 1);
                orderinfo.setDpaymenttime(new Date());
                orderinfo.setCpaymentmethod(userbankinfo.getScardtype());

                int result = orderinfoMapper.updateByPrimaryKey(orderinfo);
                if (result != 1) {
                    throw new UpdateException("mysql : update orderInfo status failed");
                }
            }

        }

        REcDeliveryinfo deliveryinfo = deliveryinfoMapper.selectByPrimaryKey(orderinfo.getNdeliveryid());
        if (deliveryinfo == null) {
            throw new UpdateException("mysql : update get deliveryInfo failed");
        }
        REcUserdeliveryaddress address = addressMapper.selectByPrimaryKey(orderinfo.getNaddressid());
        if (address == null) {
            throw new UpdateException("mysql : update get address failed");
        }
        List<REcOrdersku> orderskuList = orderskuMapper.selectByOrderId(orderId);
        if (orderskuList == null) {
            throw new UpdateException("mysql : update get orderSKUList failed");
        }

        return ConvertToDTO.convertToOrderDTO(deliveryinfo, orderinfo, orderskuList, address);
    }

    private void updateOrderPaymentByRedis(OrderDTO orderDTO) throws UpdateException{
        Jedis jedis = redisConfig.getJedis();
        String reply = null;

        String dtoJson = JSON.toJSONString(orderDTO);
        try {
            reply = jedis.set((orderDTO.getOrderId() + ""), dtoJson);
        } catch (Exception e) {
            logger.error("Redis update error: "+ e.getMessage() +" - " + orderDTO.getOrderId() + "" + ", value:" + orderDTO);
        }finally{
            redisConfig.returnResource(jedis);
        }

        if (StringUtils.isEmpty(reply)) {
            throw new UpdateException("redis : update failed");
        }
    }


    @Override
    public void createOrderAndDeliveryAndOrderSKUByShoppingCart(OrderDTO orderDTO, ShoppingCartDTO shoppingCartDTO) throws InsertException{
        OrderDTO returnOrderDTO = createOrderAndDeliveryAndOrderSKUByMySQLShoppingCart(orderDTO, shoppingCartDTO);
        createOrderAndDeliveryAndOrderSKUToRedis(returnOrderDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=Exception.class)
    public OrderDTO createOrderAndDeliveryAndOrderSKUByMySQLShoppingCart(OrderDTO orderDTO, ShoppingCartDTO shoppingCartDTO) throws InsertException {
        REcDeliveryinfo deliveryinfo = createDeliveryInfo(orderDTO);
        REcOrderinfo orderinfo = createOrderByCart(orderDTO, deliveryinfo, shoppingCartDTO);
        List<REcOrdersku> orderskuList = createOrderSKUByCart(shoppingCartDTO,orderinfo);
        REcUserdeliveryaddress address = addressMapper.selectByPrimaryKey(orderDTO.getAddressDTO().getNaddressid());
        return ConvertToDTO.convertToOrderDTO(deliveryinfo, orderinfo, orderskuList, address);
    }

    private REcOrderinfo createOrderByCart(OrderDTO orderDTO, REcDeliveryinfo deliveryinfo, ShoppingCartDTO shoppingCartDTO) throws InsertException{
        REcOrderinfo orderinfo = new REcOrderinfo();
        Date timestamp = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String timeStr = df.format(timestamp);
        String orderCode = orderDTO.getUserId() + "-order-" + timeStr;

        orderinfo.setNaddressid(orderDTO.getAddressDTO().getNaddressid());
        orderinfo.setNdeliveryid(deliveryinfo.getNdeliveryid());
        orderinfo.setNuserid(shoppingCartDTO.getUserId());
        orderinfo.setSordercode(orderCode);
        orderinfo.setScustomermark(orderDTO.getScustomermark());
        orderinfo.setSordersource(orderDTO.getSordersource());
        orderinfo.setSordertype(orderDTO.getSordertype());
        orderinfo.setSparentorderid(orderDTO.getParentOrderid());
        orderinfo.setCpaymentmethod(orderDTO.getPaymentMethod());

        if (orderinfo.getDpaymenttime() == null) {
            orderinfo.setCstatus((short) 0);
        } else {
            orderinfo.setDpaymenttime(orderDTO.getDpaymenttime());
            orderinfo.setCstatus((short) 1);
        }

        //compute and get result
        orderinfo.setNdiscount(ComputeUtils.computeDiscountByCart(shoppingCartDTO.getSkudtoList()));
        orderinfo.setNtotalprice(ComputeUtils.computeTotalPriceByCart(shoppingCartDTO.getSkudtoList(), orderDTO.getDeliveryDTO()));
        orderinfo.setNtotalquantity(ComputeUtils.computeNumberByCart(shoppingCartDTO.getSkudtoList()));

        int result = orderinfoMapper.insertSelective(orderinfo);
        if (result != 1) {
            throw new InsertException("mysql : create order by cart failed");
        }
        return orderinfo;
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=Exception.class)
    public List<REcOrdersku> createOrderSKUByCart(ShoppingCartDTO shoppingCartDTO, REcOrderinfo orderinfo) throws InsertException{
        List<SKUDTO> skudtoList = shoppingCartDTO.getSkudtoList();
        List<REcOrdersku> orderskuList = new ArrayList<>();
        for (SKUDTO skudto : skudtoList) {
            REcOrdersku ordersku = new REcOrdersku();
            ordersku.setNskuid(skudto.getSkuId());
            ordersku.setNorderid(orderinfo.getNorderid());

            if (StringUtils.isNotEmpty(skudto.getCurrency()))
                ordersku.setScurrency(skudto.getCurrency());
            ordersku.setNdiscount(skudto.getDiscount());
            ordersku.setNquantity(skudto.getQuantity());
            ordersku.setNorigprice(skudto.getPrice());
            ordersku.setNprice(skudto.getDisplayPrice());

            orderskuList.add(ordersku);
            int resultAdd = orderskuMapper.insertSelective(ordersku);
            if (resultAdd != 1) {
                throw new InsertException("mysql : create orderSKU by cart failed");
            }
            synchronized(this) {
                REcSku sku = skuMapper.selectByPrimaryKey(skudto.getSkuId());
                sku.setNinventory(sku.getNinventory() - skudto.getQuantity());
                int resultUpdate = skuMapper.updateByPrimaryKeySelective(sku);
                if (resultUpdate != 1) {
                    throw new InsertException("mysql : update sku inventory by cart failed");
                }
            }
        }
        return orderskuList;
    }

    /**
     * check method
     */
    @Override
    public boolean checkSKUParam(List<OrderSKUDTO> orderskudtoList, List<REcSku> skuList) {
        if (orderskudtoList.size() != skuList.size())
            return false;
        Collections.sort(orderskudtoList, new SortList<OrderSKUDTO>("SkuId",true));
        for (int i = 0; i < orderskudtoList.size(); i++) {
            if (!skuList.get(i).getNskuid().equals(orderskudtoList.get(i).getSkuId())
                    || orderskudtoList.get(i).getQuantity() > skuList.get(i).getNinventory() || orderskudtoList.get(i).getQuantity() <= 0
                    || orderskudtoList.get(i).getPrice().compareTo(skuList.get(i).getNdisplayprice()) != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<REcSku> getSKUListByDTOList(List<OrderSKUDTO> orderskudtoList) {
        List<Long> skuIdList = new ArrayList<>();
        orderskudtoList.forEach(n -> skuIdList.add(n.getSkuId()));
        List<REcSku> skuList = skuMapper.getSKUList(skuIdList);
        return skuList;
    }
}
