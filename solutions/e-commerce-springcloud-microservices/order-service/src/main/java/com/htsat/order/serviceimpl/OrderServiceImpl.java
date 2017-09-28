package com.htsat.order.serviceimpl;

import com.htsat.order.dao.*;
import com.htsat.order.dto.DeliveryDTO;
import com.htsat.order.dto.OrderDTO;
import com.htsat.order.dto.OrderSKUDTO;
import com.htsat.order.exception.DeleteException;
import com.htsat.order.exception.InsertException;
import com.htsat.order.exception.SearchException;
import com.htsat.order.exception.UpdateException;
import com.htsat.order.model.*;
import com.htsat.order.service.IOrderService;
import com.htsat.order.service.IRedisService;
import com.htsat.order.utils.ComputeUtils;
import com.htsat.order.utils.ConvertToDTO;
import com.htsat.order.utils.SerializeUtil;
import com.htsat.order.utils.SortList;
import org.apache.commons.lang.StringUtils;
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

@Service
public class OrderServiceImpl implements IOrderService {

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
    private IRedisService redisService;

    private Jedis getJedis(){
        return redisService.getResource();
    }

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
            REcSku sku = skuMapper.selectByPrimaryKey(orderskuDTO.getSkuId());
            sku.setNinventory(sku.getNinventory() - orderskuDTO.getQuantity());
            int resultUpdate = skuMapper.updateByPrimaryKeySelective(sku);
            if (resultUpdate != 1) {
                throw new InsertException("mysql : update sku inventory failed");
            }
        }
        return orderskuList;
    }

    private void createOrderAndDeliveryAndOrderSKUToRedis(OrderDTO orderDTO) throws InsertException{
        getJedis().set((orderDTO.getOrderId() + "").getBytes(), SerializeUtil.serialize(orderDTO));
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
        Jedis jedis = getJedis();
        byte[] orderInfo = jedis.get((orderId + "").getBytes());
        Object orderObject = SerializeUtil.unserialize(orderInfo);
        if (orderObject == null)
            return null;
        OrderDTO orderDTO = null;
        if (orderObject instanceof OrderDTO){
            orderDTO = (OrderDTO) orderObject;
        } else{
            throw new SearchException("redis : get failed");
        }
        return orderDTO;
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
        getJedis().del(orderId + "");
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
        getJedis().set((orderDTO.getOrderId() + "").getBytes(), SerializeUtil.serialize(orderDTO));
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
        getJedis().set((orderDTO.getOrderId() + "").getBytes(), SerializeUtil.serialize(orderDTO));
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
