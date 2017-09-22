package com.htsat.order.dao;

import com.htsat.order.model.REcDeliveryinfo;
import com.htsat.order.model.REcDeliveryinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface REcDeliveryinfoMapper {
    int countByExample(REcDeliveryinfoExample example);

    int deleteByExample(REcDeliveryinfoExample example);

    int deleteByPrimaryKey(String sdeliveryid);

    int insert(REcDeliveryinfo record);

    int insertSelective(REcDeliveryinfo record);

    List<REcDeliveryinfo> selectByExample(REcDeliveryinfoExample example);

    REcDeliveryinfo selectByPrimaryKey(String sdeliveryid);

    int updateByExampleSelective(@Param("record") REcDeliveryinfo record, @Param("example") REcDeliveryinfoExample example);

    int updateByExample(@Param("record") REcDeliveryinfo record, @Param("example") REcDeliveryinfoExample example);

    int updateByPrimaryKeySelective(REcDeliveryinfo record);

    int updateByPrimaryKey(REcDeliveryinfo record);
}