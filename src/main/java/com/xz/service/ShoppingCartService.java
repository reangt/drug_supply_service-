package com.xz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.domain.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {

    Integer getByDrugUserid(Integer userid, Integer drugid);

    IPage getallByid(Page<ShoppingCart> page, Integer userid);

    Boolean replaceMNameByid(Integer id, String name);
}
