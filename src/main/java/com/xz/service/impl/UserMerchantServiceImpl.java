package com.xz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.dao.UserMerchantDao;
import com.xz.domain.LoginForm;
import com.xz.domain.UserMerchant;
import com.xz.service.UserMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMerchantServiceImpl extends ServiceImpl<UserMerchantDao, UserMerchant> implements UserMerchantService {

    @Autowired
    private UserMerchantDao umd;

    @Override
    public String getByname(String name) {
        return umd.getByName(name);
    }
    @Override
    public Object saveBynameBypassword(String username, String password) {
        return umd.saveBynameBypassword(username,password);
    }

    @Override
    public UserMerchant login(LoginForm loginForm) {
        QueryWrapper<UserMerchant> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", loginForm.getPassword());
        UserMerchant userMerchant=baseMapper.selectOne(queryWrapper);
        return userMerchant;
    }

    @Override
    public String getNameById(Integer id) {
        return umd.getNameByid(id);
    }
}
