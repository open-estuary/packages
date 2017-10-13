package com.htsat.cart.serviceimpl;

import com.htsat.cart.dao.REcUserinfoMapper;
import com.htsat.cart.model.REcUserinfo;
import com.htsat.cart.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    REcUserinfoMapper userinfoMapper;

    @Override
    public boolean checkUserAvailable(Long userId) {
        REcUserinfo user =  userinfoMapper.selectByPrimaryKey(userId);
        if (user != null) {
            return true;
        }
        return false;
    }
}
