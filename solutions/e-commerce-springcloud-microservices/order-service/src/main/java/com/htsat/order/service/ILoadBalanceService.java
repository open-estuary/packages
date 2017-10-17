package com.htsat.order.service;

import com.htsat.order.serviceimpl.ClientHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-hi", fallback = ClientHystrix.class)
public interface ILoadBalanceService {
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String loadbalanceService(@RequestParam(value = "name") String name);
}
