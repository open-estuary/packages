package com.htsat.order.service;

public interface IAddressService {

    boolean checkAddressAvailable(int userId, int addressId);
}
