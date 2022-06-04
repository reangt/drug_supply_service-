package com.xz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.domain.LoginForm;
import com.xz.domain.UserMerchant;

public interface UserMerchantService extends IService<UserMerchant> {
    String getByname(String name);
//    UserMerchant getByNameAndPassword(String name, String password,Integer type);

    Object saveBynameBypassword(String username, String password);

    UserMerchant login(LoginForm loginForm);

    String getNameById(Integer id);

//    void update(UserMerchant userMerchant);
}
