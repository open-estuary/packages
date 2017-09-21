package com.htsat.order.dao;

import com.htsat.order.model.REcOrderinfo;
import com.htsat.order.model.REcOrderinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface REcOrderinfoMapper {
    int countByExample(REcOrderinfoExample example);

    int deleteByExample(REcOrderinfoExample example);

    int deleteByPrimaryKey(String sorderid);

    int insert(REcOrderinfo record);

    int insertSelective(REcOrderinfo record);

    List<REcOrderinfo> selectByExample(REcOrderinfoExample example);

    REcOrderinfo selectByPrimaryKey(String sorderid);

    int updateByExampleSelective(@Param("record") REcOrderinfo record, @Param("example") REcOrderinfoExample example);

    int updateByExample(@Param("record") REcOrderinfo record, @Param("example") REcOrderinfoExample example);

    int updateByPrimaryKeySelective(REcOrderinfo record);

    int updateByPrimaryKey(REcOrderinfo record);
}