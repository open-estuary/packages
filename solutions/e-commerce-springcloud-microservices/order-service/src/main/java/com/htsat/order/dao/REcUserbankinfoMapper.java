package com.htsat.order.dao;

import com.htsat.order.model.REcUserbankinfo;
import com.htsat.order.model.REcUserbankinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface REcUserbankinfoMapper {
    int countByExample(REcUserbankinfoExample example);

    int deleteByExample(REcUserbankinfoExample example);

    int deleteByPrimaryKey(String scardnumber);

    int insert(REcUserbankinfo record);

    int insertSelective(REcUserbankinfo record);

    List<REcUserbankinfo> selectByExample(REcUserbankinfoExample example);

    REcUserbankinfo selectByPrimaryKey(String scardnumber);

    int updateByExampleSelective(@Param("record") REcUserbankinfo record, @Param("example") REcUserbankinfoExample example);

    int updateByExample(@Param("record") REcUserbankinfo record, @Param("example") REcUserbankinfoExample example);

    int updateByPrimaryKeySelective(REcUserbankinfo record);

    int updateByPrimaryKey(REcUserbankinfo record);
}