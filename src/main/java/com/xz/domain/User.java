package com.xz.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;//用户名称
    private String password;//用户密码
    private Integer flag;//用户资格
    private String certificatepath;//用户证书地址
    private String gender;//用户性别
    private String email;//用户邮箱
    private String telephone;//用户电话
    private String address;//用户地址
    private String portraitpath;//头像地址
    private Date registrationdate;//注册日期

    public User(String name,String password){
        this.name=name;
        this.password=password;
    }
}
