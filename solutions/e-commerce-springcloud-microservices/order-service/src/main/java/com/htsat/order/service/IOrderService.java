package com.htsat.order.service;

import com.htsat.order.dto.OrderDTO;
import com.htsat.order.dto.OrderSKUDTO;
import com.htsat.order.dto.ShoppingCartDTO;
import com.htsat.order.exception.DeleteException;
import com.htsat.order.exception.InsertException;
import com.htsat.order.exception.SearchException;
import com.htsat.order.exception.UpdateException;
import com.htsat.order.model.REcSku;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface IOrderService {

    /**
     * create order
     */
    void createOrderAndDeliveryAndOrderSKU(OrderDTO orderDTO) throws InsertException;
    /**
     * get order
     */
    public OrderDTO getOrderAndDeliveryAndOrderSKUAndAddress(Long orderId) throws SearchException;

    /**
     * delete order
     */
    void deleteOrderAndDeliveryAndOrderSKU(Long orderId) throws DeleteException;

    /**
     * update order delivery
     */
    OrderDTO updateOrderDelivery(Long orderId, short deliveryStatus) throws UpdateException;

    /**
     *  update order payment
     */
    OrderDTO updateOrderPayment(Long userId, Long orderId, String cardId, String paymentPassword) throws UpdateException;

    /**
     *  ceate order cart
     */
    void createOrderAndDeliveryAndOrderSKUByShoppingCart(OrderDTO orderDTO, ShoppingCartDTO shoppingCartDTO) throws InsertException;

    /**
     *  check method
     */
    boolean checkSKUParam(List<OrderSKUDTO> orderskudtoList, List<REcSku> skuList);

    List<REcSku> getSKUListByDTOList(List<OrderSKUDTO> orderskudtoList);

}
