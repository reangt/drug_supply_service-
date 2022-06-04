package com.xz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.dao.ShoppingCartDao;
import com.xz.domain.ShoppingCart;
import com.xz.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCart> implements ShoppingCartService {

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @Override
    public Integer getByDrugUserid(Integer userid, Integer drugid) {
        return shoppingCartDao.getByDrugUserid(userid,drugid);
    }

    @Override
    public IPage getallByid(Page<ShoppingCart> page, Integer userid) {
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userid",userid);
        queryWrapper.orderByDesc("addtime");
        Page shoppingCartPage = baseMapper.selectPage(page, queryWrapper);
        return shoppingCartPage;
    }

    @Override
    public Boolean replaceMNameByid(Integer id, String name) {
        return shoppingCartDao.replaceMNameByid(id,name);
    }
}
