package com.xz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xz.domain.UserOrders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserOrdersDao extends BaseMapper<UserOrders> {
}
