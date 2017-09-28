package com.htsat.order.web;

import com.htsat.order.dto.DeliveryDTO;
import com.htsat.order.dto.OrderDTO;
import com.htsat.order.dto.StatusDTO;
import com.htsat.order.enums.ExcuteStatusEnum;
import com.htsat.order.exception.DeleteException;
import com.htsat.order.exception.InsertException;
import com.htsat.order.exception.SearchException;
import com.htsat.order.exception.UpdateException;
import com.htsat.order.service.IAddressService;
import com.htsat.order.service.IOrderService;
import com.htsat.order.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    Logger logger = Logger.getLogger(OrderController.class);

    @Autowired
    IUserService userService;

    @Autowired
    IAddressService addressService;

    @Autowired
    IOrderService orderService;

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    @ResponseBody
    public StatusDTO createOrder(@RequestBody OrderDTO orderDTO){
        StatusDTO status = new StatusDTO();
        status.setUserId(orderDTO.getUserId());
        //check
        boolean checkUserResult = userService.checkUserAvailable(orderDTO.getUserId());
        boolean checkAddressResult = addressService.checkAddressAvailable(orderDTO.getUserId(), orderDTO.getAddressDTO().getNaddressid());
        boolean checkcheckSKUPResult = orderService.checkSKUParam(orderDTO.getOrderskudtoList(), orderService.getSKUListByDTOList(orderDTO.getOrderskudtoList()));

        if (!(checkUserResult && checkAddressResult && checkcheckSKUPResult)) {
            status.setStatus(ExcuteStatusEnum.FAILURE);
            return status;
        }

        try {
            orderService.createOrderAndDeliveryAndOrderSKU(orderDTO);
        } catch (InsertException e) {
            e.printStackTrace();
            logger.error("create exception !");
            status.setStatus(ExcuteStatusEnum.FAILURE);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception !");
            status.setStatus(ExcuteStatusEnum.FAILURE);
            return status;
        }
        status.setStatus(ExcuteStatusEnum.SUCCESS);
        return status;
    }

    @RequestMapping(value = "/orders/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public OrderDTO getOrder(@PathVariable("orderId") Long orderId){
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.getOrderAndDeliveryAndOrderSKUAndAddress(orderId);
        } catch (SearchException e) {
            e.printStackTrace();
            logger.error("search exception !");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception !");
            return null;
        }
        return orderDTO;
    }

    @RequestMapping(value = "/orders/{userId}/{orderId}", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusDTO deleteOrder(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId){
        StatusDTO status = new StatusDTO();
        status.setUserId(userId);
        if (!userService.checkUserAvailable(userId)) {
            return null;
        }
        try {
            orderService.deleteOrderAndDeliveryAndOrderSKU(orderId);
        } catch (DeleteException e) {
            e.printStackTrace();
            logger.error("delete exception !");
            status.setStatus(ExcuteStatusEnum.FAILURE);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception !");
            status.setStatus(ExcuteStatusEnum.FAILURE);
            return status;
        }
        status.setStatus(ExcuteStatusEnum.SUCCESS);
        return status;
    }

    @RequestMapping(value = "/orders/delivery/{userId}/{orderId}/{deliveryStatus}", method = RequestMethod.POST)
    @ResponseBody
    public OrderDTO updateOrderStatusByDelivery(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId, @PathVariable("deliveryStatus") short deliveryStatus) {
        if (!userService.checkUserAvailable(userId) || orderId == null ) {
            return null;
        }
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.updateOrderDelivery(orderId, deliveryStatus);
        } catch (UpdateException e) {
            e.printStackTrace();
            logger.error("update delivery exception !");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("delivery exception !");
            return null;
        }
        return orderDTO;
    }

    @RequestMapping(value = "/orders/payment/{userId}/{orderId}/{cardId}/{paymentPassword}", method = RequestMethod.POST)
    @ResponseBody
    public OrderDTO updateOrderStatusByPayment(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId, @PathVariable("cardId") String cardId, @PathVariable("paymentPassword") String paymentPassword) {
        if (!userService.checkUserAvailable(userId) || orderId == null ) {
            return null;
        }
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.updateOrderPayment(userId, orderId, cardId, paymentPassword);
        } catch (UpdateException e) {
            e.printStackTrace();
            logger.error("update payment exception !");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("payment exception !");
            return null;
        }
        return orderDTO;
    }

}
