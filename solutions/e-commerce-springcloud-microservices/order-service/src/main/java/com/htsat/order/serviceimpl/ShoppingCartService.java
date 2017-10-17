package com.htsat.order.serviceimpl;

import com.htsat.order.dto.ShoppingCartDTO;
import com.htsat.order.service.ILoadBalanceService;
import com.htsat.order.service.IShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ShoppingCartService implements IShoppingCartService {
    @Override
    public ShoppingCartDTO getShoppingCart(@PathVariable("userId") Long userId) {
        return null;
    }
}
