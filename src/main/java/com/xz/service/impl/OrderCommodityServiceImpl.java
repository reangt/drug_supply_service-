package com.xz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.dao.OrderCommodityDao;
import com.xz.domain.OrderCommodity;
import com.xz.service.OrderCommodityService;
import org.springframework.stereotype.Service;

@Service
public class OrderCommodityServiceImpl extends ServiceImpl<OrderCommodityDao, OrderCommodity> implements OrderCommodityService {
    @Override
    public IPage getallByid(Page<OrderCommodity> page, Integer orderid) {
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("orderid",orderid);
        queryWrapper.orderByDesc("id");
        Page orderCommoditypage = baseMapper.selectPage(page, queryWrapper);
        return orderCommoditypage;
    }

    @Override
    public IPage getallByidm(Page<OrderCommodity> page, Integer merchantid, Integer orderid) {
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("orderid",orderid);
        queryWrapper.eq("merchantid",merchantid);
        queryWrapper.orderByDesc("id");
        Page orderCommoditypage = baseMapper.selectPage(page, queryWrapper);
        return orderCommoditypage;
    }
}
