package com.xz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xz.domain.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ShoppingCartDao extends BaseMapper<ShoppingCart> {

    @Select("select id from shoppingcart where userid=#{userid} and drugid=#{drugid}")
    Integer getByDrugUserid(Integer userid, Integer drugid);

    @Select("update shoppingcart set merchantname=#{name} where merchantid=#{id}")
    Boolean replaceMNameByid(Integer id, String name);
}
