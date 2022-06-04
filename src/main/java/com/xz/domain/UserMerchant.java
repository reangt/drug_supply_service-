package com.xz.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("usermerchant")
@Data
public class UserMerchant {
    private Integer id;
    private String name;
    private String password;
    private Object data;
    private Integer flag;
    private String certificatepath;
    private String email;
    private String telephone;
    private String address;
    private String portraitpath;
    private Date registrationdate;//注册日期

    public UserMerchant(String name,String password){
        this.name=name;
        this.password=password;
    }

    public UserMerchant() {
    }
}
