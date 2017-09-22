package com.htsat.order.dao;

import com.htsat.order.model.REcUserinfo;
import com.htsat.order.model.REcUserinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface REcUserinfoMapper {
    int countByExample(REcUserinfoExample example);

    int deleteByExample(REcUserinfoExample example);

    int deleteByPrimaryKey(Integer nuserid);

    int insert(REcUserinfo record);

    int insertSelective(REcUserinfo record);

    List<REcUserinfo> selectByExample(REcUserinfoExample example);

    REcUserinfo selectByPrimaryKey(Integer nuserid);

    int updateByExampleSelective(@Param("record") REcUserinfo record, @Param("example") REcUserinfoExample example);

    int updateByExample(@Param("record") REcUserinfo record, @Param("example") REcUserinfoExample example);

    int updateByPrimaryKeySelective(REcUserinfo record);

    int updateByPrimaryKey(REcUserinfo record);
}