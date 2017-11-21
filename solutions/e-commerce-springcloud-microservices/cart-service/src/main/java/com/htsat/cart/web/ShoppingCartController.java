package com.htsat.cart.web;

import com.htsat.cart.exception.DeleteException;
import com.htsat.cart.exception.InsertException;
import com.htsat.cart.exception.SearchException;
import com.htsat.cart.exception.UpdateException;
import com.htsat.cart.dto.ShoppingCartDTO;
import com.htsat.cart.dto.StatusDTO;
import com.htsat.cart.enums.ExcuteStatusEnum;
import com.htsat.cart.model.REcSku;
import com.htsat.cart.service.ILoadBalanceService;
import com.htsat.cart.service.IShoppingCartService;
import com.htsat.cart.service.IUserService;
import jdk.net.SocketFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShoppingCartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IUserService userService;

    @Autowired
    IShoppingCartService shoppingCartService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public StatusDTO createShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        StatusDTO status = new StatusDTO();
        if (shoppingCartDTO == null || shoppingCartDTO.getUserId() == null) {
            logger.error("shoppingCartDTO or shoppingCartDTO's userid is null");
            status.setUserId(null);
            status.setInfo("shoppingCartDTO or shoppingCartDTO's userid is null !");
            returnStatus(false, status);
            return status;
        }
        status.setUserId(shoppingCartDTO.getUserId());
        try {
            shoppingCartService.createShoppingCartAndSKU(shoppingCartDTO);
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

    @RequestMapping(value = "/{userid}/{cartid}", method = RequestMethod.GET)
    @ResponseBody
    public ShoppingCartDTO getShoppingCart(@PathVariable("userid") Long userid, @PathVariable("cartid") Long cartid){
        ShoppingCartDTO shoppingCartDTO = null;
//        if (!userService.checkUserAvailable(userid)) {
//            logger.error("User request invalid");
//            return null;
//        }
        try {
            shoppingCartDTO = shoppingCartService.getShoppingCart(cartid);
        } catch (SearchException e) {
            e.printStackTrace();
            logger.error("get exception !");
            logger.error(e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception !");
            logger.error(e.getClass().getName());
            return null;
        }
        return shoppingCartDTO;
    }

    @RequestMapping(value = "/{userid}", method = RequestMethod.GET)
    @ResponseBody
    public ShoppingCartDTO getShoppingCartByUser(@PathVariable("userid") Long userid){
        ShoppingCartDTO shoppingCartDTO = null;
//        if (!userService.checkUserAvailable(userid)) {
//            logger.error("User request invalid");
//            return null;
//        }
        try {
            shoppingCartDTO = shoppingCartService.getShoppingCartByUser(userid);
        } catch (SearchException e) {
            e.printStackTrace();
            logger.error("get exception !");
            logger.error(e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception !");
            logger.error(e.getClass().getName());
            return null;
        }
        return shoppingCartDTO;
    }

    @RequestMapping(value = "/{userid}/{cartid}", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusDTO deleteShoppingCart(@PathVariable("userid") Long userid, @PathVariable("cartid") Long cartid){
        StatusDTO status = new StatusDTO();
        status.setUserId(userid);

        try {
            shoppingCartService.deleteShoppingCartAndSKU(cartid);
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


    @RequestMapping(value = "/{userid}/{cartid}/skus/{skuid}", method = RequestMethod.POST)
    @ResponseBody
    public StatusDTO updateShoppingCartProduct(@RequestBody ShoppingCartDTO shoppingCartDTO, @PathVariable("userid") Long userid, @PathVariable("cartid") Long cartid, @PathVariable("skuid") Long skuid){
        StatusDTO status = new StatusDTO();
        status.setUserId(userid);

        try {
            shoppingCartService.updateShoppingCartSKU(shoppingCartDTO, userid, cartid, skuid);
        } catch (UpdateException e) {
            e.printStackTrace();
            logger.error("update post exception !");
            logger.error(e.getMessage());
            status.setInfo("update post exception !");
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

    @RequestMapping(value = "/{userid}/{cartid}/skus/{skuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusDTO deleteShoppingCartProduct(@PathVariable("userid") Long userid, @PathVariable("cartid") Long cartid, @PathVariable("skuid") Long skuid){
        StatusDTO status = new StatusDTO();
        status.setUserId(userid);

        try {
            shoppingCartService.deleteShoppingCartSKU(cartid, skuid);
        } catch (UpdateException e) {
            e.printStackTrace();
            logger.error("update delete exception !");
            logger.error(e.getMessage());
            status.setInfo("update delete exception !");
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
//
//    @Autowired
//    ILoadBalanceService loadBalanceService;
//
//    @RequestMapping(value = "/loadbalance")
//    public String loadbalance(@RequestParam String name){
//        return loadBalanceService.loadbalanceService(name);
//    }
}
