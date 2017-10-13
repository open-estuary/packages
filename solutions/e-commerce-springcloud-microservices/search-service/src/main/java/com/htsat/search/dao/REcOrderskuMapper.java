package com.htsat.search.dao;

import com.htsat.search.model.REcOrdersku;
import com.htsat.search.model.REcOrderskuExample;
import com.htsat.search.model.REcOrderskuKey;
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
}