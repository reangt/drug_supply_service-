package com.xz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xz.domain.UserMerchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMerchantDao extends BaseMapper<UserMerchant> {

    @Select("select name from usermerchant where name=#{name}")
    String getByName(String name);

    @Select("insert into usermerchant (name,password) values (#{name},#{password})")
    Object saveBynameBypassword(String name, String password);

    @Select("select name from usermerchant where id=#{id}")
    String getNameByid(Integer id);
}
