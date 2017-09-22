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
        deliveryDTO.setDeliveryId(deliveryinfo.getSdeliveryid());
        deliveryDTO.setDeliveryPrice(deliveryinfo.getNdeliveryprice());
        deliveryDTO.setStatus(deliveryinfo.getCstatus());
        deliveryDTO.setExpressCompany(deliveryinfo.getSexpresscompany());

        List<OrderSKUDTO> orderskuDTOList = new ArrayList<>();
        for (REcOrdersku ordersku : orderskuList) {
            OrderSKUDTO orderskuDTO = new OrderSKUDTO();
            orderskuDTO.setOrderId(ordersku.getSorderid());
            orderskuDTO.setSkuId(ordersku.getNskuid());
            orderskuDTO.setCurrency(ordersku.getScurrency());
            orderskuDTO.setDiscount(ordersku.getNdiscount());
            orderskuDTO.setQuantity(ordersku.getNquantity());
            orderskuDTO.setOriginPrice(ordersku.getNorigprice());
            orderskuDTO.setPrice(ordersku.getNprice());

            orderskuDTOList.add(orderskuDTO);
        }

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setUserId(address.getNuserid());
        addressDTO.setAddressId(address.getNaddressno());
        addressDTO.setAddress(address.getSaddress());
        addressDTO.setCity(address.getScity());
        addressDTO.setCountry(address.getScountry());
        addressDTO.setProvince(address.getSprovince());
        addressDTO.setEmail(address.getSemail());
        addressDTO.setFirstName(address.getSfirstname());
        addressDTO.setLastName(address.getSlastname());
        addressDTO.setPhone(address.getSphoneno());

        orderDTO.setAddressDTO(addressDTO);
        orderDTO.setCompleteDate(orderinfo.getSdateCompleted());
        orderDTO.setCustomerMark(orderinfo.getScustomermark());
        orderDTO.setDeliveryDTO(deliveryDTO);
        orderDTO.setDiscount(orderinfo.getNdiscount());
        orderDTO.setOrderId(orderinfo.getSorderid());
        orderDTO.setOrderskudtoList(orderskuDTOList);
        orderDTO.setPaidDate(orderinfo.getSdatePaid());
        orderDTO.setParentOrderid(orderinfo.getSparentorderid());
        orderDTO.setPaymentMethod(orderinfo.getCpaymentmethod());
        orderDTO.setPaymentMethodTitle(orderinfo.getSpaymentmethodtitle());
        orderDTO.setStatus(orderinfo.getCstatus());
        orderDTO.setTotalPrice(orderinfo.getNtotalprice());
        orderDTO.setTotalQuantity(orderinfo.getNtotalquantity());
        orderDTO.setUserId(orderinfo.getNuserid());
        orderDTO.setVersion(orderinfo.getSversion());

        return orderDTO;

    }
}
