package com.htsat.cart.serviceimpl;

import com.htsat.cart.service.ILoadBalanceService;
import org.springframework.stereotype.Service;

@Service
public class ClientHystrix implements ILoadBalanceService {

    @Override
    public String loadbalanceService(String name) {
        return "cart failed!";
    }
}
