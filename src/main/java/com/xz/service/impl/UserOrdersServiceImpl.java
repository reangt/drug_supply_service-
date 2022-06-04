package com.xz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.dao.UserOrdersDao;
import com.xz.domain.UserOrders;
import com.xz.service.UserOrdersService;
import org.springframework.stereotype.Service;

@Service
public class UserOrdersServiceImpl extends ServiceImpl<UserOrdersDao, UserOrders> implements UserOrdersService {
    @Override
    public IPage getallByid(Page<UserOrders> page, int userid) {
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userid",userid);
        queryWrapper.orderByDesc("addtime");
        Page orderspage = baseMapper.selectPage(page, queryWrapper);
        return orderspage;
    }
}
