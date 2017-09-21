package com.htsat.order.web;

import com.htsat.order.dto.DeliveryDTO;
import com.htsat.order.dto.OrderDTO;
import com.htsat.order.dto.StatusDTO;
import com.htsat.order.enums.ExcuteStatusEnum;
import com.htsat.order.service.IAddressService;
import com.htsat.order.service.IOrderService;
import com.htsat.order.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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
        OrderDTO returnOrderDTO = null;
        //check
        boolean checkUserResult = userService.checkUserAvailable(orderDTO.getUserId());
        boolean checkAddressResult = addressService.checkAddressAvailable(orderDTO.getUserId(), orderDTO.getAddressDTO().getAddressId());
        boolean checkcheckSKUPResult = orderService.checkSKUParam(orderDTO.getOrderskudtoList(), orderService.getSKUListByDTOList(orderDTO.getOrderskudtoList()));

        if (!(checkUserResult && checkAddressResult && checkcheckSKUPResult)) {
            status.setStatus(ExcuteStatusEnum.FAILURE);
            return status;
        }

        try {
            //mysql
            returnOrderDTO = orderService.createOrderAndDeliveryAndOrderSKU(orderDTO);
            //redis
            orderService.createOrderAndDeliveryAndOrderSKUToRedis(returnOrderDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("create exception !");
            status.setStatus(ExcuteStatusEnum.FAILURE);
            return status;
        }
        status.setStatus(ExcuteStatusEnum.SUCCESS);
        return status;
    }

    @RequestMapping(value = "/orders/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public OrderDTO getOrder(@PathVariable("orderId") String orderId){
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.getOrderAndDeliveryAndOrderSKUAndAddress(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("get exception !");
            return null;
        }
        return orderDTO;
    }

    @RequestMapping(value = "/orders/{userId}/{orderId}", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusDTO deleteOrder(@PathVariable("userId") Integer userId, @PathVariable("orderId") String orderId){
        StatusDTO status = new StatusDTO();
        status.setUserId(userId);
        if (!userService.checkUserAvailable(userId)) {
            return null;
        }
        try {
            orderService.deleteOrderAndDeliveryAndOrderSKU(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("delete exception !");
            status.setStatus(ExcuteStatusEnum.FAILURE);
            return status;
        }
        status.setStatus(ExcuteStatusEnum.SUCCESS);
        return status;
    }

    @RequestMapping(value = "/orders/{userId}/{orderId}/{deliveryStatus}", method = RequestMethod.POST)
    @ResponseBody
    public OrderDTO updateOrderStatusByDelivery(@PathVariable("userId") Integer userId, @PathVariable("orderId") String orderId, @PathVariable("deliveryStatus") String deliveryStatus) {
        if (!userService.checkUserAvailable(userId) || StringUtils.isEmpty(orderId) || !NumberUtils.isNumber(deliveryStatus)) {
            return null;
        }
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.updateOrderDelivery(orderId, deliveryStatus);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("update delivery exception !");
            return null;
        }
        return orderDTO;
    }

    @RequestMapping(value = "/orders/payment", method = RequestMethod.POST)
    @ResponseBody
    public OrderDTO updateOrderStatusAndPaymentMethodByPayment(@RequestBody OrderDTO orderDTO) {
        return null;
    }

}
