package com.xz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xz.domain.Drug;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DrugDao extends BaseMapper<Drug> {

    @Select("select * from drug where id=#{id}")
    Drug getByid(Integer id);
}
