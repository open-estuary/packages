package com.htsat.order.service;

import com.htsat.order.dto.OrderDTO;
import com.htsat.order.dto.OrderSKUDTO;
import com.htsat.order.model.REcSku;
import java.util.List;

public interface IOrderService {

    /**
     * create order
     */
    OrderDTO createOrderAndDeliveryAndOrderSKU(OrderDTO orderDTO) throws Exception;

    void createOrderAndDeliveryAndOrderSKUToRedis(OrderDTO orderDTO) throws Exception;

    /**
     * get order
     */
    OrderDTO getOrderAndDeliveryAndOrderSKUAndAddress(String orderId) throws Exception;

    /**
     * delete order
     */
    void deleteOrderAndDeliveryAndOrderSKU(String orderId) throws Exception;

    /**
     * update order delivery
     */
    OrderDTO updateOrderDelivery(String orderId, String deliveryStatus) throws Exception;

    /**
     *  check method
     */
    boolean checkSKUParam(List<OrderSKUDTO> orderskudtoList, List<REcSku> skuList);

    List<REcSku> getSKUListByDTOList(List<OrderSKUDTO> orderskudtoList);

}
