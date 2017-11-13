package com.htsat.order.dao;

import com.htsat.order.model.REcOrderinfo;
import com.htsat.order.model.REcOrderinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface REcOrderinfoMapper {
    int countByExample(REcOrderinfoExample example);

    int deleteByExample(REcOrderinfoExample example);

    int deleteByPrimaryKey(Long norderid);

    int insert(REcOrderinfo record);

    int insertSelective(REcOrderinfo record);

    List<REcOrderinfo> selectByExample(REcOrderinfoExample example);

    REcOrderinfo selectByPrimaryKey(Long norderid);

    int updateByExampleSelective(@Param("record") REcOrderinfo record, @Param("example") REcOrderinfoExample example);

    int updateByExample(@Param("record") REcOrderinfo record, @Param("example") REcOrderinfoExample example);

    int updateByPrimaryKeySelective(REcOrderinfo record);

    int updateByPrimaryKey(REcOrderinfo record);

    List<REcOrderinfo> selectByUserId(Long userid);
}