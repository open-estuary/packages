package com.htsat.order.utils;

import com.htsat.order.dto.DeliveryDTO;
import com.htsat.order.dto.OrderSKUDTO;
import com.htsat.order.dto.SKUDTO;

import java.math.BigDecimal;
import java.util.List;

public class ComputeUtils {
    public static int computeNumber(List<OrderSKUDTO> orderSKUDTOList) {
        int quantity = 0;
        for (int i = 0; i < orderSKUDTOList.size(); i++) {
            quantity += orderSKUDTOList.get(i).getQuantity();
        }
        return quantity;
    }

    public static BigDecimal computeDiscount(List<OrderSKUDTO> orderSKUDTOList) {
        BigDecimal discount = new BigDecimal(0);
        for (int i = 0; i < orderSKUDTOList.size(); i++) {
            BigDecimal quantity = new BigDecimal(orderSKUDTOList.get(i).getQuantity());
            BigDecimal sDiscount = orderSKUDTOList.get(i).getDiscount();
            if (sDiscount == null)
                sDiscount = new BigDecimal(0);
            discount = discount.add(sDiscount.multiply(quantity));
        }
        return discount;
    }

    public static BigDecimal computeTotalPrice(List<OrderSKUDTO> orderSKUDTOList, DeliveryDTO deliveryDTO) {
        BigDecimal totalPrice = new BigDecimal(0);
        BigDecimal deliveryPrice = deliveryDTO.getNdeliveryprice();
        for (int i = 0; i < orderSKUDTOList.size(); i++) {
            BigDecimal displayPrice = orderSKUDTOList.get(i).getPrice();
            BigDecimal quantity = new BigDecimal(orderSKUDTOList.get(i).getQuantity());
            BigDecimal price = displayPrice.multiply(quantity);

            totalPrice = totalPrice.add(price);
        }
        totalPrice = totalPrice.add(deliveryPrice);
        return totalPrice;
    }


    /*****************************************shopping cart****************************************/
    public static int computeNumberByCart(List<SKUDTO> skuDTOList) {
        int quantity = 0;
        for (int i = 0; i < skuDTOList.size(); i++) {
            quantity += skuDTOList.get(i).getQuantity();
        }
        return quantity;
    }

    public static BigDecimal computeDiscountByCart(List<SKUDTO> skuDTOList) {
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

    public static BigDecimal computeTotalPriceByCart(List<SKUDTO> skuDTOList, DeliveryDTO deliveryDTO) {
        BigDecimal totalPrice = new BigDecimal(0);
        BigDecimal deliveryPrice = deliveryDTO.getNdeliveryprice();
        for (int i = 0; i < skuDTOList.size(); i++) {
            BigDecimal displayPrice = skuDTOList.get(i).getDisplayPrice();
            BigDecimal quantity = new BigDecimal(skuDTOList.get(i).getQuantity());
            BigDecimal price = displayPrice.multiply(quantity);
            totalPrice = totalPrice.add(price);
        }
        totalPrice = totalPrice.add(deliveryPrice);
        return totalPrice;
    }
}
