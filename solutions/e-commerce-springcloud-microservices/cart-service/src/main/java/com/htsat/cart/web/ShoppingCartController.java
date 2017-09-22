package com.htsat.cart.web;

import com.htsat.cart.dto.ShoppingCartDTO;
import com.htsat.cart.dto.StatusDTO;
import com.htsat.cart.enums.ExcuteStatusEnum;
import com.htsat.cart.model.REcShoppingcart;
import com.htsat.cart.model.REcSku;
import com.htsat.cart.service.IRedisService;
import com.htsat.cart.service.IShoppingCartService;
import com.htsat.cart.service.IUserService;
import com.htsat.cart.utils.ConvertToDTO;
import com.htsat.cart.utils.SerializeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.List;

@RestController
public class ShoppingCartController {

    Logger logger = Logger.getLogger(ShoppingCartController.class);

    @Autowired
    IUserService userService;

    @Autowired
    IShoppingCartService shoppingCartService;

//    @Autowired
//    IRedisService redisService;

    @RequestMapping(value = "/carts", method = RequestMethod.POST)
    @ResponseBody
    public StatusDTO createShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){
        StatusDTO status = new StatusDTO();
        status.setUserId(shoppingCartDTO.getUserId());

        List<REcSku> skuList = shoppingCartService.getSKUListByDTOList(shoppingCartDTO.getSkudtoList());
        if (!userService.checkUserAvailable(shoppingCartDTO.getUserId())
                || !shoppingCartService.checkSKUParam(shoppingCartDTO.getSkudtoList(), skuList)) {
            status.setStatus(ExcuteStatusEnum.FAILURE);
            return status;
        }

//        Jedis jedis = redisService.getResource();
        ShoppingCartDTO returnShoppingCartDTO = null;
        try {
            //mysql save
            returnShoppingCartDTO = shoppingCartService.addShoppingCartAndSKU(shoppingCartDTO);
            //redis save
            shoppingCartService.addShoppCartAndSKUToRedis(returnShoppingCartDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("create exception !");
            status.setStatus(ExcuteStatusEnum.FAILURE);
            return status;
        }
        status.setStatus(ExcuteStatusEnum.SUCCESS);
        return status;
    }

    @RequestMapping(value = "/carts/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ShoppingCartDTO getShoppingCart(@PathVariable("userId") Integer userId){
        ShoppingCartDTO shoppingCartDTO = null;
        if (!userService.checkUserAvailable(userId)) {
            return null;
        }
        try {
            //redis get
            shoppingCartDTO = shoppingCartService.getShoppingCartFromRedis(userId);
            //mysql get
            if (shoppingCartDTO == null) {
                REcShoppingcart shoppingcart = shoppingCartService.getShoppingCart(userId);
                List<REcSku> skuList = shoppingCartService.getSKUList(userId);
                shoppingCartDTO = ConvertToDTO.convertToShoppingDTO(shoppingcart, skuList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("get exception !");
            return null;
        }
        return shoppingCartDTO;
    }

    @RequestMapping(value = "/carts/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusDTO deleteShoppingCart(@PathVariable("userId") Integer userId){
        StatusDTO status = new StatusDTO();
        status.setUserId(userId);

        if (!userService.checkUserAvailable(userId)) {
            return null;
        }

        try {
            shoppingCartService.deleteShoppingCartAndSKU(userId);
            shoppingCartService.deleteShoppingCartAndSKUToRedis(userId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("delete exception !");
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
            //mysql
            returnShoppingCartDTO = shoppingCartService.updateShoppingCartAndSKU(type, shoppingCartDTO);
            //redis
            shoppingCartService.updateShoppingCartAndSKUToRedis(returnShoppingCartDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("delete exception !");
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
            logger.info("create fail !");
        }
        return status;
    }

//    @RequestMapping("/redis/set")
//    public String redisSet(){
//        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
//        shoppingCartDTO.setUserId(1);
//        Jedis jedis = redisService.getResource();
//        jedis.set((shoppingCartDTO.getUserId() + "").getBytes(), SerializeUtil.serialize(shoppingCartDTO));
//
//        return "true";
//    }
//
//    @RequestMapping("/redis/get")
//    public ShoppingCartDTO redisGet(){
//        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
//        shoppingCartDTO.setUserId(1);
//        Jedis jedis = redisService.getResource();
//        byte[] person = jedis.get((shoppingCartDTO.getUserId() + "").getBytes());
//        return (ShoppingCartDTO) SerializeUtil.unserialize(person);
//    }

//    @Autowired
//    RestTemplate restTemplate;
//
//    @RequestMapping(value = "/add", method = RequestMethod.GET)
//    public String add() {
//        return restTemplate.getForEntity("http://CART-SERVICE/add?a=10&b=20", String.class).getBody();
//    }

}
