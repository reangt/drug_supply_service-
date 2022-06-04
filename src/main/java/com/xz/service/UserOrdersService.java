package com.xz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.domain.UserOrders;

public interface UserOrdersService extends IService<UserOrders> {
    IPage getallByid(Page<UserOrders> page, int userid);
}
