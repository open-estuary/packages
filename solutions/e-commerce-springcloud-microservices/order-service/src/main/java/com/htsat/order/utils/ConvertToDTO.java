package com.htsat.order.utils;

import com.htsat.order.dto.AddressDTO;
import com.htsat.order.dto.DeliveryDTO;
import com.htsat.order.dto.OrderDTO;
import com.htsat.order.dto.OrderSKUDTO;
import com.htsat.order.model.REcDeliveryinfo;
import com.htsat.order.model.REcOrderinfo;
import com.htsat.order.model.REcOrdersku;
import com.htsat.order.model.REcUserdeliveryaddress;

import java.util.ArrayList;
import java.util.List;

public class ConvertToDTO {

    public static OrderDTO convertToOrderDTO(REcDeliveryinfo deliveryinfo, REcOrderinfo orderinfo, List<REcOrdersku> orderskuList, REcUserdeliveryaddress address) {

        OrderDTO orderDTO = new OrderDTO();

        DeliveryDTO deliveryDTO = new DeliveryDTO();
        deliveryDTO.setNdeliveryid(deliveryinfo.getNdeliveryid());
        deliveryDTO.setNdeliveryprice(deliveryinfo.getNdeliveryprice());
        deliveryDTO.setCstatus(deliveryinfo.getCstatus());
        deliveryDTO.setSexpresscompany(deliveryinfo.getSexpresscompany());
        deliveryDTO.setNaddressid(deliveryinfo.getNaddressid());
        deliveryDTO.setSconsignee(deliveryinfo.getSconsignee());
        deliveryDTO.setSdeliverycode(deliveryinfo.getSdeliverycode());
        deliveryDTO.setSdeliverycomment(deliveryinfo.getSdeliverycomment());

        List<OrderSKUDTO> orderskuDTOList = new ArrayList<>();
        for (REcOrdersku ordersku : orderskuList) {
            OrderSKUDTO orderskuDTO = new OrderSKUDTO();
            orderskuDTO.setOrderId(ordersku.getNorderid());
            orderskuDTO.setSkuId(ordersku.getNskuid());
            orderskuDTO.setCurrency(ordersku.getScurrency());
            orderskuDTO.setDiscount(ordersku.getNdiscount());
            orderskuDTO.setQuantity(ordersku.getNquantity());
            orderskuDTO.setOriginPrice(ordersku.getNorigprice());
            orderskuDTO.setPrice(ordersku.getNprice());

            orderskuDTOList.add(orderskuDTO);
        }

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setNuserid(address.getNuserid());
        addressDTO.setNaddressid(address.getNaddressid());
        addressDTO.setSaddress(address.getSaddress());
        addressDTO.setScity(address.getScity());
        addressDTO.setScountry(address.getScountry());
        addressDTO.setSprovince(address.getSprovince());
        addressDTO.setSemail(address.getSemail());
        addressDTO.setSfirstname(address.getSfirstname());
        addressDTO.setSlastname(address.getSlastname());
        addressDTO.setSphoneno(address.getSphoneno());

        orderDTO.setAddressDTO(addressDTO);
        orderDTO.setDeliveryDTO(deliveryDTO);
        orderDTO.setScompletedtime(orderinfo.getScompletedtime());
        orderDTO.setScustomermark(orderinfo.getScustomermark());
        orderDTO.setDiscount(orderinfo.getNdiscount());
        orderDTO.setOrderId(orderinfo.getNorderid());
        orderDTO.setSordercode(orderinfo.getSordercode());
        orderDTO.setOrderskudtoList(orderskuDTOList);
        orderDTO.setDpaymenttime(orderinfo.getDpaymenttime());
        orderDTO.setParentOrderid(orderinfo.getSparentorderid());
        orderDTO.setPaymentMethod(orderinfo.getCpaymentmethod());
        orderDTO.setCstatus(orderinfo.getCstatus());
        orderDTO.setTotalPrice(orderinfo.getNtotalprice());
        orderDTO.setTotalQuantity(orderinfo.getNtotalquantity());
        orderDTO.setUserId(orderinfo.getNuserid());

        return orderDTO;

    }
}
