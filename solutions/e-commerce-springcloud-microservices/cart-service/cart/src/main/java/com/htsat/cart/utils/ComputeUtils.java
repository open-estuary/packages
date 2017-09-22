package com.htsat.cart.utils;

import com.htsat.cart.dto.SKUDTO;

import java.math.BigDecimal;
import java.util.List;

public class ComputeUtils {

    public static int computeNumber(List<SKUDTO> skuDTOList) {
        int quantity = 0;
        for (int i = 0; i < skuDTOList.size(); i++) {
            quantity += skuDTOList.get(i).getQuantity();
        }
        return quantity;
    }

    public static float computeDiscount(List<SKUDTO> skuDTOList) {
        float discount = 0;
        for (int i = 0; i < skuDTOList.size(); i++) {
            discount += skuDTOList.get(i).getDiscount() * skuDTOList.get(i).getQuantity();
        }
        return discount;
    }

    public static BigDecimal computeTotalPrice(List<SKUDTO> skuDTOList) {
        BigDecimal totalPrice = new BigDecimal(0);
        for (int i = 0; i < skuDTOList.size(); i++) {
            BigDecimal displayPrice = skuDTOList.get(i).getDisplayPrice();
            BigDecimal quantity = new BigDecimal(skuDTOList.get(i).getQuantity());
            BigDecimal price = displayPrice.multiply(quantity);
            totalPrice = totalPrice.add(price);
        }
        return totalPrice;
    }
}
