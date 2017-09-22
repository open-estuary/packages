package com.htsat.cart.dao;

import com.htsat.cart.model.REcCartsku;
import com.htsat.cart.model.REcCartskuExample;
import com.htsat.cart.model.REcCartskuKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface REcCartskuMapper {
    int countByExample(REcCartskuExample example);

    int deleteByExample(REcCartskuExample example);

    int deleteByPrimaryKey(REcCartskuKey key);

    int insert(REcCartsku record);

    int insertSelective(REcCartsku record);

    List<REcCartsku> selectByExample(REcCartskuExample example);

    REcCartsku selectByPrimaryKey(REcCartskuKey key);

    int updateByExampleSelective(@Param("record") REcCartsku record, @Param("example") REcCartskuExample example);

    int updateByExample(@Param("record") REcCartsku record, @Param("example") REcCartskuExample example);

    int updateByPrimaryKeySelective(REcCartsku record);

    int updateByPrimaryKey(REcCartsku record);

    List<REcCartsku> selectByUserId(int userId);
}