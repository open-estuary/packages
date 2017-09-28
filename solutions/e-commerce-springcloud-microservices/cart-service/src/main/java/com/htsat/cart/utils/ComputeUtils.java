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

    public static BigDecimal computeDiscount(List<SKUDTO> skuDTOList) {
        BigDecimal discount = new BigDecimal(0);
        for (int i = 0; i < skuDTOList.size(); i++) {
            BigDecimal quantity = new BigDecimal(skuDTOList.get(i).getQuantity());
            BigDecimal sDiscount = skuDTOList.get(i).getDiscount();
            if (sDiscount == null)
                sDiscount = new BigDecimal(0);
            discount = discount.add(sDiscount.multiply(quantity));
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
