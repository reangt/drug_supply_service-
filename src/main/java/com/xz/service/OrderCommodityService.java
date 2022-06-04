package com.xz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.domain.OrderCommodity;

public interface OrderCommodityService extends IService<OrderCommodity> {
    IPage getallByid(Page<OrderCommodity> page, Integer orderid);

    IPage getallByidm(Page<OrderCommodity> page, Integer merchantid, Integer orderid);
}
