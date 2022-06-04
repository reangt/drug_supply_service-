package com.xz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.dao.MerchantOrdersDao;
import com.xz.domain.MerchantOrders;
import com.xz.service.MerchantOrdersService;
import org.springframework.stereotype.Service;

@Service
public class MerchantOrdersServiceImpl extends ServiceImpl<MerchantOrdersDao, MerchantOrders> implements MerchantOrdersService {
    @Override
    public IPage getallByid(Page<MerchantOrders> page, int merchantid) {
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("merchantid",merchantid);
        queryWrapper.orderByDesc("addtime");
        Page MerchantOrderspage = baseMapper.selectPage(page, queryWrapper);
        return MerchantOrderspage;
    }
}
