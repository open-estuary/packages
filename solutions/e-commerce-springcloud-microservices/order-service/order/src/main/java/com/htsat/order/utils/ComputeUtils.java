package com.htsat.order.utils;

import com.htsat.order.dto.DeliveryDTO;
import com.htsat.order.dto.OrderSKUDTO;

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

    public static float computeDiscount(List<OrderSKUDTO> orderSKUDTOList) {
        float discount = 0;
        for (int i = 0; i < orderSKUDTOList.size(); i++) {
            discount += orderSKUDTOList.get(i).getDiscount() * orderSKUDTOList.get(i).getQuantity();
        }
        return discount;
    }

    public static BigDecimal computeTotalPrice(List<OrderSKUDTO> orderSKUDTOList, DeliveryDTO deliveryDTO) {
        BigDecimal totalPrice = new BigDecimal(0);
        for (int i = 0; i < orderSKUDTOList.size(); i++) {
            BigDecimal displayPrice = orderSKUDTOList.get(i).getPrice();
            BigDecimal quantity = new BigDecimal(orderSKUDTOList.get(i).getQuantity());
            BigDecimal deliveryPrice = deliveryDTO.getDeliveryPrice();
            BigDecimal price = displayPrice.multiply(quantity).add(deliveryPrice);

            totalPrice = totalPrice.add(price);
        }
        return totalPrice;
    }
}
