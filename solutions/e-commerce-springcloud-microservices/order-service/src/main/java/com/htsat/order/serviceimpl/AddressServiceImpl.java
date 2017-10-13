package com.htsat.order.serviceimpl;

import com.htsat.order.dao.REcUserdeliveryaddressMapper;
import com.htsat.order.model.REcUserdeliveryaddress;
import com.htsat.order.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    REcUserdeliveryaddressMapper addressMapper;

    @Override
    public boolean checkAddressAvailable(Long userId, Long addressId) {
        REcUserdeliveryaddress address = addressMapper.selectByPrimaryKey(addressId);
        if (address != null && userId.equals(address.getNuserid())) {
            return true;
        }
        return false;
    }
}
