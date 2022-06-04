package com.xz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xz.domain.DrugType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface DrugTypeDao extends BaseMapper<DrugType> {

    @Select("select id,producttype from drugtype where producttype is not null")
    List<Map<Integer,String>> getproducttype();

    @Select("select id,packtype from drugtype where packtype is not null")
    List<Map<Integer, String>> getpacktype();

    @Select("select id,usetype from drugtype where usetype is not null")
    List<Map<Integer, String>> getusetype();

    @Select("select id,packagingunit from drugtype where packagingunit is not null")
    List<Map<Integer, String>> getpackagingunit();

    @Select("select packagingunit from drugtype where id=#{i}")
    String getTypeByIdpai(int i,String type);

    @Select("select usetype from drugtype where id=#{i}")
    String getTypeByIdut(int i,String type);

    @Select("select packtype from drugtype where id=#{i}")
    String getTypeByIdpt(int i,String type);

    @Select("select producttype from drugtype where id=#{i}")
    String getTypeByIdpr(int i,String type);
}
