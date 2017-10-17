package com.htsat.order.service;

import com.htsat.order.dto.ShoppingCartDTO;
import com.htsat.order.serviceimpl.ShoppingCartService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "cart-service", fallback = ShoppingCartService.class)
public interface IShoppingCartService {
    @RequestMapping(value = "/carts/{userId}", method = RequestMethod.GET)
    @ResponseBody
    ShoppingCartDTO getShoppingCart(@PathVariable("userId") Long userId);
}
