package com.htsat.order.serviceimpl;

import com.htsat.order.service.ILoadBalanceService;
import org.springframework.stereotype.Service;

@Service
public class ClientHystrix implements ILoadBalanceService {
    @Override
    public String loadbalanceService(String name) {
        return "order failed";
    }
}
