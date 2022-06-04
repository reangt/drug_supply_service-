package com.xz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.domain.LoginForm;
import com.xz.domain.User;

public interface UserService extends IService<User> {
    String getByname(String name);
    User getByNameAndPassword(String name,String password);

    User login(LoginForm loginForm);

    Integer saveBynameBypassword(String username, String password);
}
