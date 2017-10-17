package com.htsat.cart.utils;

import com.htsat.cart.dto.SKUDTO;
import com.htsat.cart.dto.ShoppingCartDTO;
import com.htsat.cart.model.REcShoppingcart;
import com.htsat.cart.model.REcSku;

import java.util.ArrayList;
import java.util.List;

public class ConvertToDTO {

    public static ShoppingCartDTO convertToShoppingDTO(REcShoppingcart shoppingCart, List<REcSku> skuList) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setNshoppingcartid(shoppingCart.getNshoppingcartid());
        shoppingCartDTO.setUserId(shoppingCart.getNuserid());
        shoppingCartDTO.setNcreatetime(shoppingCart.getNcreatetime());
        shoppingCartDTO.setSupdatetime(shoppingCart.getSupdatetime());
        shoppingCartDTO.setCurrency(shoppingCart.getScurrency());
        shoppingCartDTO.setDiscount(shoppingCart.getNdiscount());
        shoppingCartDTO.setQuantity(shoppingCart.getNtotalquantity());
        shoppingCartDTO.setPrice(shoppingCart.getNtotalprice());

        List<SKUDTO> skudtoList = new ArrayList<>();
        for (REcSku sku : skuList) {
            skudtoList.add(convertToSKUDTO(sku));
        }
        shoppingCartDTO.setSkudtoList(skudtoList);
        return shoppingCartDTO;
    }

    public static SKUDTO convertToSKUDTO(REcSku sku) {
        SKUDTO skudto = new SKUDTO();
        skudto.setSpuId(sku.getNspuid());
        skudto.setSkuId(sku.getNskuid());
        skudto.setColor(sku.getNcolor());
        skudto.setSize(sku.getSsize());
        skudto.setPrice(sku.getNprice());
        skudto.setDisplayPrice(sku.getNdisplayprice());
        //
        skudto.setQuantity(sku.getNinventory());
        skudto.setDiscount(sku.getNdiscount());
        skudto.setCurrency(sku.getScurrency());
        skudto.setDcreatetime(sku.getDcreatetime());
        skudto.setDupdatetime(sku.getDupdatetime());
        return skudto;
    }
}
