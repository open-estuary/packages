package com.htsat.order.dao;

import com.htsat.order.model.REcOrdersku;
import com.htsat.order.model.REcOrderskuExample;
import java.util.List;

import com.htsat.order.model.REcOrderskuKey;
import org.apache.ibatis.annotations.Param;

public interface REcOrderskuMapper {
    int countByExample(REcOrderskuExample example);

    int deleteByExample(REcOrderskuExample example);

    int deleteByPrimaryKey(Long norderskuid);

    int insert(REcOrdersku record);

    int insertSelective(REcOrdersku record);

    List<REcOrdersku> selectByExample(REcOrderskuExample example);

    REcOrdersku selectByPrimaryKey(Long norderskuid);

    int updateByExampleSelective(@Param("record") REcOrdersku record, @Param("example") REcOrderskuExample example);

    int updateByExample(@Param("record") REcOrdersku record, @Param("example") REcOrderskuExample example);

    int updateByPrimaryKeySelective(REcOrdersku record);

    int updateByPrimaryKey(REcOrdersku record);

    List<REcOrdersku> selectByOrderId(Long orderId);

    int deleteByOrderskuKey(REcOrderskuKey key);
}