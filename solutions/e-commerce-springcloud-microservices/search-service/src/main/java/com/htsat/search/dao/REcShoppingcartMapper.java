package com.htsat.search.dao;

import com.htsat.search.model.REcShoppingcart;
import com.htsat.search.model.REcShoppingcartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface REcShoppingcartMapper {
    int countByExample(REcShoppingcartExample example);

    int deleteByExample(REcShoppingcartExample example);

    int deleteByPrimaryKey(Integer nuserid);

    int insert(REcShoppingcart record);

    int insertSelective(REcShoppingcart record);

    List<REcShoppingcart> selectByExample(REcShoppingcartExample example);

    REcShoppingcart selectByPrimaryKey(Integer nuserid);

    int updateByExampleSelective(@Param("record") REcShoppingcart record, @Param("example") REcShoppingcartExample example);

    int updateByExample(@Param("record") REcShoppingcart record, @Param("example") REcShoppingcartExample example);

    int updateByPrimaryKeySelective(REcShoppingcart record);

    int updateByPrimaryKey(REcShoppingcart record);
}