package com.htsat.order.dao;

import com.htsat.order.model.REcImage;
import com.htsat.order.model.REcImageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface REcImageMapper {
    int countByExample(REcImageExample example);

    int deleteByExample(REcImageExample example);

    int deleteByPrimaryKey(Integer nimageid);

    int insert(REcImage record);

    int insertSelective(REcImage record);

    List<REcImage> selectByExample(REcImageExample example);

    REcImage selectByPrimaryKey(Integer nimageid);

    int updateByExampleSelective(@Param("record") REcImage record, @Param("example") REcImageExample example);

    int updateByExample(@Param("record") REcImage record, @Param("example") REcImageExample example);

    int updateByPrimaryKeySelective(REcImage record);

    int updateByPrimaryKey(REcImage record);
}