package com.htsat.order.service;

public interface IAddressService {

    boolean checkAddressAvailable(Long userId, Long addressId);
}
