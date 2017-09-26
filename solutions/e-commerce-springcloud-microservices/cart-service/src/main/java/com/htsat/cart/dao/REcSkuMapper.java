package com.htsat.cart.dao;

import com.htsat.cart.model.REcSku;
import com.htsat.cart.model.REcSkuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface REcSkuMapper {
    int countByExample(REcSkuExample example);

    int deleteByExample(REcSkuExample example);

    int deleteByPrimaryKey(Long nskuid);

    int insert(REcSku record);

    int insertSelective(REcSku record);

    List<REcSku> selectByExample(REcSkuExample example);

    REcSku selectByPrimaryKey(Long nskuid);

    int updateByExampleSelective(@Param("record") REcSku record, @Param("example") REcSkuExample example);

    int updateByExample(@Param("record") REcSku record, @Param("example") REcSkuExample example);

    int updateByPrimaryKeySelective(REcSku record);

    int updateByPrimaryKey(REcSku record);

    List<REcSku> getSKUList(List<Long> nskuidList);
}