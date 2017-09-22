package com.htsat.cart.service;

import com.htsat.cart.dto.SKUDTO;
import com.htsat.cart.dto.ShoppingCartDTO;
import com.htsat.cart.model.REcCartsku;
import com.htsat.cart.model.REcShoppingcart;
import com.htsat.cart.model.REcSku;

import java.util.List;

public interface IShoppingCartService {
    /*********************************************check******************************************/

    boolean checkSKUParam(List<SKUDTO> skudtoList, List<REcSku> skuList);

    List<REcSku> getSKUListByDTOList(List<SKUDTO> skudtoList);

    List<REcSku> getSKUListBycarskuList(List<REcCartsku> cartskuList);

    /*********************************************create******************************************/

    ShoppingCartDTO addShoppingCartAndSKU(ShoppingCartDTO shoppingCartDTO) throws Exception;

    void addShoppCartAndSKUToRedis(ShoppingCartDTO returnShoppingCartDTO) throws Exception;

    /*********************************************search******************************************/

    List<REcSku> getSKUList(int userId);

    REcShoppingcart getShoppingCart(int userId) throws Exception;

    ShoppingCartDTO getShoppingCartFromRedis(int userId) throws Exception;

    /*********************************************delete******************************************/

    void deleteShoppingCartAndSKU(int userId) throws Exception;

    void deleteShoppingCartAndSKUToRedis(int userId) throws Exception;

    /*********************************************update******************************************/

    ShoppingCartDTO updateShoppingCartAndSKU(int type, ShoppingCartDTO shoppingCartDTO) throws Exception;

    void updateShoppingCartAndSKUToRedis(ShoppingCartDTO returnShoppingCartDTO) throws Exception;

}
