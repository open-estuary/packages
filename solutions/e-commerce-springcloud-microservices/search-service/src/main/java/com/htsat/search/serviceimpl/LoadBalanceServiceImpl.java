package com.htsat.search.serviceimpl;

import com.htsat.search.service.ILoadBalanceService;
import org.springframework.stereotype.Component;

@Component
public class LoadBalanceServiceImpl implements ILoadBalanceService{

    @Override
    public String loadbalanceService(String name) {
        return "failed!";
    }
}
