package com.xz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xz.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao extends BaseMapper<User> {

    @Select("select name from user where name=#{name}")
    String getByName(String name);

    @Select("select * from user where name=#{name} and password=#{password}")
    User getByNameAndPassword(String name,String password);

    @Select("insert into user (name,password) values (#{name},#{password})")
    Integer saveByNameAndPassword(String name, String password);
}
