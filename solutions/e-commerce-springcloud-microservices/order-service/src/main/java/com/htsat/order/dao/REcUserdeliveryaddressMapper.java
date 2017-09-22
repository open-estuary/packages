package com.htsat.order.dao;

import com.htsat.order.model.REcUserdeliveryaddress;
import com.htsat.order.model.REcUserdeliveryaddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface REcUserdeliveryaddressMapper {
    int countByExample(REcUserdeliveryaddressExample example);

    int deleteByExample(REcUserdeliveryaddressExample example);

    int deleteByPrimaryKey(Integer naddressno);

    int insert(REcUserdeliveryaddress record);

    int insertSelective(REcUserdeliveryaddress record);

    List<REcUserdeliveryaddress> selectByExample(REcUserdeliveryaddressExample example);

    REcUserdeliveryaddress selectByPrimaryKey(Integer naddressno);

    int updateByExampleSelective(@Param("record") REcUserdeliveryaddress record, @Param("example") REcUserdeliveryaddressExample example);

    int updateByExample(@Param("record") REcUserdeliveryaddress record, @Param("example") REcUserdeliveryaddressExample example);

    int updateByPrimaryKeySelective(REcUserdeliveryaddress record);

    int updateByPrimaryKey(REcUserdeliveryaddress record);
}