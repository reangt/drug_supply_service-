package com.xz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.controller.utils.MD5;
import com.xz.dao.UserDao;
import com.xz.domain.LoginForm;
import com.xz.domain.User;
import com.xz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public String getByname(String name) {
        return userDao.getByName(name);
    }

    @Override
    public User getByNameAndPassword(String name, String password) {
        return userDao.getByNameAndPassword(name,password);
    }

    @Override
    public User login(LoginForm loginForm) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
//        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        queryWrapper.eq("password", loginForm.getPassword());
        User user = baseMapper.selectOne(queryWrapper);

//        String name=loginForm.getUsername();
//        String password=loginForm.getPassword();
//        System.out.println("name："+name+"  "+"password："+password);
//        User user=userDao.getByNameAndPassword(name,password);
//        System.out.println(user);
        return user;
    }

    @Override
    public Integer saveBynameBypassword(String username, String password) {
        return userDao.saveByNameAndPassword(username,password);
    }
}
