package com.htsat.order.serviceimpl;

import com.htsat.order.dao.REcUserinfoMapper;
import com.htsat.order.model.REcUserinfo;
import com.htsat.order.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    REcUserinfoMapper userinfoMapper;

    @Override
    public boolean checkUserAvailable(int userId) {
        REcUserinfo user = userinfoMapper.selectByPrimaryKey(userId);
        if (user != null) {
            return true;
        }
        return false;
    }
}
