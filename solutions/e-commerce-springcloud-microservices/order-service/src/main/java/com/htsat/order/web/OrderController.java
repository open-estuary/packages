package com.htsat.order.web;

import com.htsat.order.dto.OrderDTO;
import com.htsat.order.dto.ShoppingCartDTO;
import com.htsat.order.dto.StatusDTO;
import com.htsat.order.enums.ExcuteStatusEnum;
import com.htsat.order.exception.DeleteException;
import com.htsat.order.exception.InsertException;
import com.htsat.order.exception.SearchException;
import com.htsat.order.exception.UpdateException;
import com.htsat.order.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IUserService userService;

    @Autowired
    IAddressService addressService;

    @Autowired
    IOrderService orderService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public StatusDTO createOrder(@RequestBody OrderDTO orderDTO){
        StatusDTO status = new StatusDTO();
        status.setUserId(orderDTO.getUserId());

        try {
            orderService.createOrderAndDeliveryAndOrderSKU(orderDTO);
        } catch (InsertException e) {
            e.printStackTrace();
            logger.error("create exception !");
            logger.error(e.getMessage());
            status.setInfo("create exception !");
            status.setError(e.getMessage());
            returnStatus(false, status);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception !");
            logger.error(e.getClass().getName());
            status.setInfo("exception !");
            status.setError(e.getClass().getName());
            returnStatus(false, status);
            return status;
        }
        returnStatus(true, status);
        return status;
    }

    @RequestMapping(value = "/{userid}/{orderid}", method = RequestMethod.GET)
    @ResponseBody
    public OrderDTO getOrder(@PathVariable("userid") Long userid, @PathVariable("orderid") Long orderid){

        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.getOrderAndDeliveryAndOrderSKUAndAddress(userid,orderid);
        } catch (SearchException e) {
            e.printStackTrace();
            logger.error("search exception !");
            logger.error(e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception !");
            logger.error(e.getClass().getName());
            return null;
        }
        return orderDTO;
    }

    @RequestMapping(value = "/{userid}", method = RequestMethod.GET)
    @ResponseBody
    public List<OrderDTO> getAllOrder(@PathVariable("userid") Long userid){

        List<OrderDTO> orderDTOList = null;
        try {
            orderDTOList = orderService.getAllOrderAndDeliveryAndOrderSKUAndAddress(userid);
        } catch (SearchException e) {
            e.printStackTrace();
            logger.error("search all exception !");
            logger.error(e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception !");
            logger.error(e.getClass().getName());
            return null;
        }
        return orderDTOList;
    }

    @RequestMapping(value = "/{userid}/{orderid}", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusDTO deleteOrder(@PathVariable("userid") Long userid, @PathVariable("orderid") Long orderid){
        StatusDTO status = new StatusDTO();
        status.setUserId(userid);

        try {
            orderService.deleteOrderAndDeliveryAndOrderSKU(orderid);
        } catch (DeleteException e) {
            e.printStackTrace();
            logger.error("delete exception !");
            logger.error(e.getMessage());
            status.setInfo("delete exception !");
            status.setError(e.getMessage());
            returnStatus(false, status);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception !");
            logger.error(e.getClass().getName());
            status.setInfo("exception !");
            status.setError(e.getClass().getName());
            returnStatus(false, status);
            return status;
        }
        returnStatus(true, status);
        return status;
    }

    private StatusDTO returnStatus(boolean result, StatusDTO status) {
        if (result) {
            status.setStatus(ExcuteStatusEnum.SUCCESS);
            logger.info("execute success !");
        } else{
            status.setStatus(ExcuteStatusEnum.FAILURE);
            logger.error("execute fail !");
        }
        return status;
    }

//    @RequestMapping(value = "/orders/delivery", method = RequestMethod.POST)
//    @ResponseBody
//    public OrderDTO updateOrderStatusByDelivery(@RequestParam("userId") Long userId, @RequestParam("orderId") Long orderId, @RequestParam("deliveryStatus") short deliveryStatus) {
//        if (!userService.checkUserAvailable(userId) || orderId == null ) {
//            return null;
//        }
//        OrderDTO orderDTO = null;
//        try {
//            orderDTO = orderService.updateOrderDelivery(orderId, deliveryStatus);
//        } catch (UpdateException e) {
//            e.printStackTrace();
//            logger.error("update delivery exception !");
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("delivery exception !");
//            return null;
//        }
//        return orderDTO;
//    }
//
//    @RequestMapping(value = "/orders/payment", method = RequestMethod.POST)
//    @ResponseBody
//    public OrderDTO updateOrderStatusByPayment(@RequestParam("userId") Long userId, @RequestParam("orderId") Long orderId, @RequestParam("cardId") String cardId, @RequestParam("paymentPassword") String paymentPassword) {
//        if (!userService.checkUserAvailable(userId) || orderId == null ) {
//            return null;
//        }
//        OrderDTO orderDTO = null;
//        try {
//            orderDTO = orderService.updateOrderPayment(userId, orderId, cardId, paymentPassword);
//        } catch (UpdateException e) {
//            e.printStackTrace();
//            logger.error("update payment exception !");
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("payment exception !");
//            return null;
//        }
//        return orderDTO;
//    }

//    @Autowired
//    ILoadBalanceService loadbalanceService;
//
//    @RequestMapping(value = "/loadbalance")
//    public String loadbalance(@RequestParam String name){
//        return loadbalanceService.loadbalanceService(name);
//    }

//    @Autowired
//    IShoppingCartService shoppingCartService;
//
//    @RequestMapping(value = "/ordersCart", method = RequestMethod.POST)
//    public StatusDTO createOrderByShoppingCart(@RequestBody OrderDTO orderDTO) {
//        StatusDTO status = new StatusDTO();
//        status.setUserId(orderDTO.getUserId());
//
//        ShoppingCartDTO shoppingCartDTO = shoppingCartService.getShoppingCart(orderDTO.getUserId());
//
//        boolean checkUserResult = userService.checkUserAvailable(shoppingCartDTO.getUserId());
//        boolean checkAddressResult = addressService.checkAddressAvailable(shoppingCartDTO.getUserId(), orderDTO.getAddressDTO().getNaddressid());
////        boolean checkcheckSKUPResult = orderService.checkSKUParam(orderDTO.getOrderskudtoList(), orderService.getSKUListByDTOList(orderDTO.getOrderskudtoList()));
//
//        if (!(checkUserResult && checkAddressResult/* && checkcheckSKUPResult*/)) {
//            status.setStatus(ExcuteStatusEnum.FAILURE);
//            return status;
//        }
//
//        try {
//            orderService.createOrderAndDeliveryAndOrderSKUByShoppingCart(orderDTO, shoppingCartDTO);
//        } catch (InsertException e) {
//            e.printStackTrace();
//            logger.error("create by cart exception !");
//            status.setStatus(ExcuteStatusEnum.FAILURE);
//            return status;
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("exception !");
//            status.setStatus(ExcuteStatusEnum.FAILURE);
//            return status;
//        }
//        status.setStatus(ExcuteStatusEnum.SUCCESS);
//        return status;
//    }
}
