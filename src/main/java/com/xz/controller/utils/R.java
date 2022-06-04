package com.xz.controller.utils;

import lombok.Data;

//设计表现层返回的模型类，用于前后端数据格式统一
@Data
public class R {
    private Boolean flag=false;
    private Object data=null;
    private String msg=null;

    public R(Boolean flag){
        this.flag=flag;
    }
    public R(Object data){
//        this.flag=flag;
        this.data=data;
    }
    public R(Boolean flag,String msg){
        this.flag=flag;
        this.msg=msg;
    }

    public R(Boolean flag, Object data, String msg) {
        this.flag = flag;
        this.data = data;
        this.msg = msg;
    }

    public R(Boolean flag, Object data){
        this.flag=flag;
        this.data=data;
    }
    public R(){}
}
