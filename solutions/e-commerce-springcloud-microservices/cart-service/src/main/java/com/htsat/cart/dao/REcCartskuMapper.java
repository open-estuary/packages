package com.htsat.cart.dao;

import com.htsat.cart.model.REcCartsku;
import com.htsat.cart.model.REcCartskuExample;
import java.util.List;

import com.htsat.cart.model.REcCartskuKey;
import org.apache.ibatis.annotations.Param;

public interface REcCartskuMapper {
    int countByExample(REcCartskuExample example);

    int deleteByExample(REcCartskuExample example);

    int deleteByPrimaryKey(Long ncartskuid);

    int insert(REcCartsku record);

    int insertSelective(REcCartsku record);

    List<REcCartsku> selectByExample(REcCartskuExample example);

    REcCartsku selectByPrimaryKey(Long ncartskuid);

    int updateByExampleSelective(@Param("record") REcCartsku record, @Param("example") REcCartskuExample example);

    int updateByExample(@Param("record") REcCartsku record, @Param("example") REcCartskuExample example);

    int updateByPrimaryKeySelective(REcCartsku record);

    int updateByPrimaryKey(REcCartsku record);

    List<REcCartsku> selectByShoppingCartId(Long userId);

    int deleteByREcCartskuKey(REcCartskuKey cartskuKey);

    REcCartsku selectByREcCartskuKey(REcCartskuKey cartskuKey);
}