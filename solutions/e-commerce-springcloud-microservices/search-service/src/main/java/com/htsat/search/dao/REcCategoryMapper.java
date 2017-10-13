package com.htsat.search.dao;

import com.htsat.search.model.REcCategory;
import com.htsat.search.model.REcCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface REcCategoryMapper {
    int countByExample(REcCategoryExample example);

    int deleteByExample(REcCategoryExample example);

    int deleteByPrimaryKey(Integer ncategoryid);

    int insert(REcCategory record);

    int insertSelective(REcCategory record);

    List<REcCategory> selectByExample(REcCategoryExample example);

    REcCategory selectByPrimaryKey(Integer ncategoryid);

    int updateByExampleSelective(@Param("record") REcCategory record, @Param("example") REcCategoryExample example);

    int updateByExample(@Param("record") REcCategory record, @Param("example") REcCategoryExample example);

    int updateByPrimaryKeySelective(REcCategory record);

    int updateByPrimaryKey(REcCategory record);
}