package com.htsat.order.serviceimpl;

import com.htsat.order.dao.*;
import com.htsat.order.dto.OrderDTO;
import com.htsat.order.dto.OrderSKUDTO;
import com.htsat.order.enums.DeliveryStatusEnum;
import com.htsat.order.enums.OrderStatusEnum;
import com.htsat.order.model.*;
import com.htsat.order.service.IOrderService;
import com.htsat.order.service.IRedisService;
import com.htsat.order.utils.ComputeUtils;
import com.htsat.order.utils.ConvertToDTO;
import com.htsat.order.utils.SerializeUtil;
import com.htsat.order.utils.SortList;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.sql.Timestamp;
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
    private IRedisService redisService;

    private Jedis getJedis(){
        return redisService.getResource();
    }

    /**
     * create order
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=Exception.class)
    public OrderDTO createOrderAndDeliveryAndOrderSKU(OrderDTO orderDTO) throws Exception {
        REcDeliveryinfo deliveryinfo = createDeliveryInfo(orderDTO);
        REcOrderinfo orderinfo = createOrder(orderDTO);
        List<REcOrdersku> orderskuList = createOrderSKU(orderDTO,orderinfo);
        REcUserdeliveryaddress address = addressMapper.selectByPrimaryKey(orderDTO.getAddressDTO().getAddressId());
        return ConvertToDTO.convertToOrderDTO(deliveryinfo, orderinfo, orderskuList, address);
    }

    private REcDeliveryinfo createDeliveryInfo(OrderDTO orderDTO) throws Exception{
        REcDeliveryinfo deliveryinfo = new REcDeliveryinfo();
        deliveryinfo.setSdeliveryid(orderDTO.getDeliveryDTO().getDeliveryId());
        deliveryinfo.setNdeliveryprice(orderDTO.getDeliveryDTO().getDeliveryPrice());
//        deliveryinfo.setCstatus(orderDTO.getDeliveryDTO().getStatus());
        deliveryinfo.setCstatus("0");
        if (StringUtils.isNotEmpty(orderDTO.getDeliveryDTO().getExpressCompany()))
            deliveryinfo.setSexpresscompany(orderDTO.getDeliveryDTO().getExpressCompany());
        int result = deliveryinfoMapper.insert(deliveryinfo);
        if (result != 1) {
            throw new Exception();
        }
        return deliveryinfo;
    }

    private REcOrderinfo createOrder(OrderDTO orderDTO) throws Exception{
        REcOrderinfo orderinfo = new REcOrderinfo();
        Date timestamp = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String timeStr = df.format(timestamp);
        String orderId = orderDTO.getUserId() + ":" + timeStr;
        orderinfo.setSorderid(orderId);
        orderinfo.setNuserid(orderDTO.getUserId());//
        orderinfo.setSparentorderid(orderDTO.getParentOrderid());
        orderinfo.setSversion(orderDTO.getVersion());
        orderinfo.setNaddressno(orderDTO.getAddressDTO().getAddressId());

        orderinfo.setScustomermark(orderDTO.getCustomerMark());
        orderinfo.setSdateCreated(new Date());//
        orderinfo.setSdateModified(new Date());//
        if (StringUtils.isNotEmpty(orderDTO.getPaymentMethod())) {
            orderinfo.setSdatePaid(new Date());
            orderinfo.setCpaymentmethod(orderDTO.getPaymentMethod());
            orderinfo.setSpaymentmethodtitle(orderDTO.getPaymentMethodTitle());
            orderinfo.setCstatus("1");//1
        } else {
            orderinfo.setCstatus("0");//0
        }
        orderinfo.setSdeliveryid(orderDTO.getDeliveryDTO().getDeliveryId());//后续需要改成表自动生成

        //compute and get result
        orderinfo.setNdiscount(ComputeUtils.computeDiscount(orderDTO.getOrderskudtoList()));
        orderinfo.setNtotalprice(ComputeUtils.computeTotalPrice(orderDTO.getOrderskudtoList(), orderDTO.getDeliveryDTO()));//
        orderinfo.setNtotalquantity(ComputeUtils.computeNumber(orderDTO.getOrderskudtoList()));//

        int result = orderinfoMapper.insert(orderinfo);
        if (result != 1) {
            throw new Exception();
        }
        return orderinfo;
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=Exception.class)
    public List<REcOrdersku> createOrderSKU(OrderDTO orderDTO, REcOrderinfo orderinfo) throws Exception{
        List<OrderSKUDTO> skudtoList = orderDTO.getOrderskudtoList();
        List<REcOrdersku> orderskuList = new ArrayList<>();
        for (OrderSKUDTO orderskuDTO : skudtoList) {
            REcOrdersku ordersku = new REcOrdersku();
            ordersku.setNskuid(orderskuDTO.getSkuId());
            ordersku.setSorderid(orderinfo.getSorderid());
            if (StringUtils.isNotEmpty(orderskuDTO.getCurrency()))
                ordersku.setScurrency(orderskuDTO.getCurrency());
            ordersku.setNdiscount(orderskuDTO.getDiscount());
            ordersku.setNquantity(orderskuDTO.getQuantity());
            ordersku.setNorigprice(orderskuDTO.getOriginPrice());
            ordersku.setNprice(orderskuDTO.getPrice());

            orderskuList.add(ordersku);
            int resultAdd = orderskuMapper.insert(ordersku);
            if (resultAdd != 1) {
                throw new Exception();
            }
            REcSku sku = skuMapper.selectByPrimaryKey(orderskuDTO.getSkuId());
            sku.setNinventory(sku.getNinventory() - orderskuDTO.getQuantity());
            int resultUpdate = skuMapper.updateByPrimaryKeySelective(sku);
            if (resultUpdate != 1) {
                throw new Exception();
            }
        }
        return orderskuList;
    }

    @Override
    public void createOrderAndDeliveryAndOrderSKUToRedis(OrderDTO orderDTO) throws Exception{
        getJedis().set(orderDTO.getOrderId().getBytes(), SerializeUtil.serialize(orderDTO));
    }

    /**
     * get order
     */
    @Override
    public OrderDTO getOrderAndDeliveryAndOrderSKUAndAddress(String orderId) throws Exception {
        OrderDTO orderDTO = getOrderInfoByRedis(orderId);
        if (orderDTO == null )  orderDTO = getOrderInfoByMySQL(orderId);
        return orderDTO;
    }

    private OrderDTO getOrderInfoByMySQL(String orderId) throws Exception {
        REcOrderinfo orderinfo = orderinfoMapper.selectByPrimaryKey(orderId);
        if (orderinfo == null){
            throw new Exception();
        } else {
            String deliveryId = orderinfo.getSdeliveryid();
            REcDeliveryinfo deliveryinfo = deliveryinfoMapper.selectByPrimaryKey(deliveryId);
            if (deliveryinfo == null) {
               throw new Exception();
            }
            int addressId = orderinfo.getNaddressno();
            REcUserdeliveryaddress address = addressMapper.selectByPrimaryKey(addressId);
            if (address == null) {
                throw new Exception();
            }
            List<REcOrdersku> orderskuList = orderskuMapper.selectByOrderId(orderId);
            if (orderskuList == null) {
                throw new Exception();
            }
            return ConvertToDTO.convertToOrderDTO(deliveryinfo, orderinfo, orderskuList, address);
        }
    }

    private OrderDTO getOrderInfoByRedis(String orderId) throws Exception {
        Jedis jedis = getJedis();
        byte[] orderInfo = jedis.get(orderId.getBytes());
        Object orderObject = SerializeUtil.unserialize(orderInfo);
        if (orderObject == null)
            return null;
        OrderDTO orderDTO = null;
        if (orderObject instanceof OrderDTO){
            orderDTO = (OrderDTO) orderObject;
        } else{
            throw new Exception();
        }
        return orderDTO;
    }

    /**
     * delete order
     */
    @Override
    public void deleteOrderAndDeliveryAndOrderSKU(String orderId) throws Exception {
        deleteOrderInfoByMySQL(orderId);
        deleteOrderInfoByRedis(orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=Exception.class)
    public void deleteOrderInfoByMySQL(String orderId) throws Exception {
        REcOrderinfo orderinfo = orderinfoMapper.selectByPrimaryKey(orderId);
        List<REcOrdersku> orderskuList = orderskuMapper.selectByOrderId(orderId);
        //add inventory of sku
        for (REcOrdersku ordersku : orderskuList) {
            REcSku sku = skuMapper.selectByPrimaryKey(ordersku.getNskuid());
            sku.setNinventory(sku.getNinventory() + ordersku.getNquantity());
            int resultInventory = skuMapper.updateByPrimaryKey(sku);
            if (resultInventory != 1) {
                throw new Exception();
            }
        }
        //delete delivery
        int resultDelivery = deliveryinfoMapper.deleteByPrimaryKey(orderinfo.getSdeliveryid());
        if (resultDelivery != 1) {
            throw new Exception();
        }
        //delete order
        int resultOrder = orderinfoMapper.deleteByPrimaryKey(orderId);
        if (resultOrder != 1) {
            throw new Exception();
        }
        //delete ordersku
        for (REcOrdersku ordersku : orderskuList) {
            REcOrderskuKey key = new REcOrderskuKey();
            key.setSorderid(orderId);
            key.setNskuid(ordersku.getNskuid());
            int resultOrderSKU = orderskuMapper.deleteByPrimaryKey(key);
            if (resultOrderSKU != 1) {
                throw new Exception();
            }
        }
    }

    private void deleteOrderInfoByRedis(String orderId) throws Exception {
        getJedis().del(orderId);
    }

    /**
     * update order delivery
     * order  0：未支付;1：已支付未发货;2：已发货;3：已接收;4：已关闭
     * delivery  1：已收件;2：在途;3：待签收;4：已签收
     */
    @Override
    public OrderDTO updateOrderDelivery(String orderId, String deliveryStatus) throws Exception {
        OrderDTO orderDTO = updateOrderDeliveryByMySQL(orderId, deliveryStatus);
        updateOrderDeliveryByRedis(orderDTO);
        return orderDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=Exception.class)
    public OrderDTO updateOrderDeliveryByMySQL(String orderId, String deliveryStatus) throws Exception{
        REcOrderinfo orderinfo = orderinfoMapper.selectByPrimaryKey(orderId);
        if (orderinfo == null) {
            throw new Exception();
        }
        REcDeliveryinfo deliveryinfo = deliveryinfoMapper.selectByPrimaryKey(orderinfo.getSdeliveryid());
        if (deliveryinfo == null) {
            throw new Exception();
        }

        //update delivery
        deliveryinfo.setCstatus(deliveryStatus);
        int resultDelivery = deliveryinfoMapper.updateByPrimaryKey(deliveryinfo);
        if (resultDelivery != 1) {
            throw new Exception();
        }

        //update order
        if (deliveryStatus.equals("4")) {//delivery has sign , order receive
            orderinfo.setCstatus("3");
        } else if (deliveryStatus.equals("1")
                    || deliveryStatus.equals("2")
                    || deliveryStatus.equals("3")){
            orderinfo.setCstatus("2");
        } else {
            //do nothing
        }
        int resultOrder = orderinfoMapper.updateByPrimaryKey(orderinfo);
        if (resultOrder != 1) {
            throw new Exception();
        }

        REcUserdeliveryaddress address = addressMapper.selectByPrimaryKey(orderinfo.getNaddressno());
        if (address == null) {
            throw new Exception();
        }

        List<REcOrdersku> orderskuList = orderskuMapper.selectByOrderId(orderId);
        if (orderskuList == null) {
            throw new Exception();
        }

        return ConvertToDTO.convertToOrderDTO(deliveryinfo, orderinfo, orderskuList, address);
    }

    private void updateOrderDeliveryByRedis(OrderDTO orderDTO) throws Exception{
        getJedis().set(orderDTO.getOrderId().getBytes(), SerializeUtil.serialize(orderDTO));
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
                    || orderskudtoList.get(i).getPrice().compareTo(skuList.get(i).getNdisplayPrice()) != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<REcSku> getSKUListByDTOList(List<OrderSKUDTO> orderskudtoList) {
        List<Integer> skuIdList = new ArrayList<>();
        orderskudtoList.forEach(n -> skuIdList.add(n.getSkuId()));
        List<REcSku> skuList = skuMapper.getSKUList(skuIdList);
        return skuList;
    }
}
