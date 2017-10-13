package com.htsat.cart.dao;

import com.htsat.cart.model.REcBrand;
import com.htsat.cart.model.REcBrandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface REcBrandMapper {
    int countByExample(REcBrandExample example);

    int deleteByExample(REcBrandExample example);

    int deleteByPrimaryKey(Integer nbrandid);

    int insert(REcBrand record);

    int insertSelective(REcBrand record);

    List<REcBrand> selectByExample(REcBrandExample example);

    REcBrand selectByPrimaryKey(Integer nbrandid);

    int updateByExampleSelective(@Param("record") REcBrand record, @Param("example") REcBrandExample example);

    int updateByExample(@Param("record") REcBrand record, @Param("example") REcBrandExample example);

    int updateByPrimaryKeySelective(REcBrand record);

    int updateByPrimaryKey(REcBrand record);
}