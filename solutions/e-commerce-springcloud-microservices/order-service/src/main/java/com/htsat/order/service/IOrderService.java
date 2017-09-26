package com.htsat.order.service;

import com.htsat.order.dto.OrderDTO;
import com.htsat.order.dto.OrderSKUDTO;
import com.htsat.order.model.REcSku;

import java.util.List;

public interface IOrderService {

    /**
     * create order
     */
    void createOrderAndDeliveryAndOrderSKU(OrderDTO orderDTO) throws Exception;
    /**
     * get order
     */
    public OrderDTO getOrderAndDeliveryAndOrderSKUAndAddress(Long orderId) throws Exception;

    /**
     * delete order
     */
    void deleteOrderAndDeliveryAndOrderSKU(Long orderId) throws Exception;

    /**
     * update order delivery
     */
    OrderDTO updateOrderDelivery(Long orderId, short deliveryStatus) throws Exception;

    /**
     *  update order payment
     */
    OrderDTO updateOrderPayment(Long userId, Long orderId, String cardId, String paymentPassword) throws Exception;

    /**
     *  check method
     */
    boolean checkSKUParam(List<OrderSKUDTO> orderskudtoList, List<REcSku> skuList);

    List<REcSku> getSKUListByDTOList(List<OrderSKUDTO> orderskudtoList);

}
