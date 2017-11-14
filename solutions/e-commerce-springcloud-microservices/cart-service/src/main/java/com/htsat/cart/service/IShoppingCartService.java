package com.htsat.cart.service;

import com.htsat.cart.exception.DeleteException;
import com.htsat.cart.exception.SearchException;
import com.htsat.cart.exception.UpdateException;
import com.htsat.cart.dto.SKUDTO;
import com.htsat.cart.dto.ShoppingCartDTO;
import com.htsat.cart.model.REcCartsku;
import com.htsat.cart.model.REcSku;

import java.util.List;

public interface IShoppingCartService {
    /*********************************************check******************************************/

    boolean checkSingleSKUParam(SKUDTO skudto, REcSku sku);

    boolean checkSKUParam(List<SKUDTO> skudtoList, List<REcSku> skuList);

    List<REcSku> getSKUListByDTOList(List<SKUDTO> skudtoList);

    List<REcSku> getSKUListBycarskuList(List<REcCartsku> cartskuList);

    /*********************************************create******************************************/

    void addShoppingCartAndSKU(ShoppingCartDTO shoppingCartDTO) throws Exception;

    /*********************************************search******************************************/

    ShoppingCartDTO getShoppingCartByUser(Long userid) throws SearchException;

    ShoppingCartDTO getShoppingCart(Long shoppingcartid) throws SearchException;

    /*********************************************delete******************************************/

    void deleteShoppingCartAndSKU(Long userId) throws DeleteException;

    /*********************************************update******************************************/

//    ShoppingCartDTO updateShoppingCartAndSKU(int type, ShoppingCartDTO shoppingCartDTO) throws UpdateException;

    void updateShoppingCartSKU(ShoppingCartDTO shoppingCartDTO) throws UpdateException;

    void deleteShoppingCartSKU(Long cartid, Long skuid) throws UpdateException;

}
