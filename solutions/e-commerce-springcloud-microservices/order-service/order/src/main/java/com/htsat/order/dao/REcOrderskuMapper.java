package com.htsat.order.dao;

import com.htsat.order.model.REcOrdersku;
import com.htsat.order.model.REcOrderskuExample;
import com.htsat.order.model.REcOrderskuKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface REcOrderskuMapper {
    int countByExample(REcOrderskuExample example);

    int deleteByExample(REcOrderskuExample example);

    int deleteByPrimaryKey(REcOrderskuKey key);

    int insert(REcOrdersku record);

    int insertSelective(REcOrdersku record);

    List<REcOrdersku> selectByExample(REcOrderskuExample example);

    REcOrdersku selectByPrimaryKey(REcOrderskuKey key);

    int updateByExampleSelective(@Param("record") REcOrdersku record, @Param("example") REcOrderskuExample example);

    int updateByExample(@Param("record") REcOrdersku record, @Param("example") REcOrderskuExample example);

    int updateByPrimaryKeySelective(REcOrdersku record);

    int updateByPrimaryKey(REcOrdersku record);

    List<REcOrdersku> selectByOrderId(String orderId);
}