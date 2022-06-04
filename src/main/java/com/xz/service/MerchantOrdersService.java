package com.xz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.domain.MerchantOrders;

public interface MerchantOrdersService extends IService<MerchantOrders> {
    IPage getallByid(Page<MerchantOrders> page, int merchantid);
}
