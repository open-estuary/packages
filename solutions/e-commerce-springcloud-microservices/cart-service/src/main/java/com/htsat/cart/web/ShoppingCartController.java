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

    @RequestMapping(value = "/carts", method = RequestMethod.POST)
    @ResponseBody
    public StatusDTO createShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){
        StatusDTO status = new StatusDTO();
        status.setUserId(shoppingCartDTO.getUserId());

        List<REcSku> skuList = shoppingCartService.getSKUListByDTOList(shoppingCartDTO.getSkudtoList());
        //用户 sku 校验
        if (!userService.checkUserAvailable(shoppingCartDTO.getUserId())
                || !shoppingCartService.checkSKUParam(shoppingCartDTO.getSkudtoList(), skuList)) {
            status.setStatus(ExcuteStatusEnum.FAILURE);
            return status;
        }

        ShoppingCartDTO shoppingCartDTOCheck;
        try {
            shoppingCartDTOCheck = shoppingCartService.getShoppingCart(shoppingCartDTO.getUserId());
        } catch (SearchException e) {
            e.printStackTrace();
            logger.error("check exception !");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception !");
            return null;
        }

        if (shoppingCartDTOCheck != null) {
            logger.error("create replication !");
            status.setStatus(ExcuteStatusEnum.FAILURE);
            return status;
        }

        try {
            shoppingCartService.addShoppingCartAndSKU(shoppingCartDTO);
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

    @RequestMapping(value = "/carts", method = RequestMethod.GET)
    @ResponseBody
    public ShoppingCartDTO getShoppingCart(@RequestParam("userId") Long userId, @RequestParam("shoppingcartid") Long shoppingcartid){
        ShoppingCartDTO shoppingCartDTO = null;
        if (!userService.checkUserAvailable(userId)) {
            return null;
        }
        try {
            shoppingCartDTO = shoppingCartService.getShoppingCart(shoppingcartid);
        } catch (SearchException e) {
            e.printStackTrace();
            logger.error("get exception !");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception !");
            return null;
        }
        return shoppingCartDTO;
    }

    @RequestMapping(value = "/carts", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusDTO deleteShoppingCart(@RequestParam("userId") Long userId){
        StatusDTO status = new StatusDTO();
        status.setUserId(userId);

        if (!userService.checkUserAvailable(userId)) {
            status.setStatus(ExcuteStatusEnum.FAILURE);
            return status;
        }

        try {
            shoppingCartService.deleteShoppingCartAndSKU(userId);
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
        returnStatus(true, status);
        return status;
    }

    @RequestMapping(value = "/carts/{type}", method = RequestMethod.POST)
    @ResponseBody
    public ShoppingCartDTO updateShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO, @PathVariable("type") Integer type) {
        if (!userService.checkUserAvailable(shoppingCartDTO.getUserId()) || type > 3 || type < 1) {
            return null;
        }
        ShoppingCartDTO returnShoppingCartDTO = null;
        try {
            returnShoppingCartDTO = shoppingCartService.updateShoppingCartAndSKU(type, shoppingCartDTO);
        } catch (UpdateException e) {
            e.printStackTrace();
            logger.error("update exception !");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception !");
            return null;
        }
        return returnShoppingCartDTO;
    }

    private StatusDTO returnStatus(boolean result, StatusDTO status) {
        if (result) {
            status.setStatus(ExcuteStatusEnum.SUCCESS);
            logger.info("create success !");
        } else{
            status.setStatus(ExcuteStatusEnum.FAILURE);
            logger.error("create fail !");
        }
        return status;
    }

    @Autowired
    ILoadBalanceService loadBalanceService;

    @RequestMapping(value = "/loadbalance")
    public String loadbalance(@RequestParam String name){
        return loadBalanceService.loadbalanceService(name);
    }
}
