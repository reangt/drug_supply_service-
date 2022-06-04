package com.xz.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("drug")
@Data
public class Drug {
    private Integer id;
    private String name;//名称
    private Double price;//价格
    private String specification;//规格
    private String manufacturer;//厂家
    private String productiondate;//生产日期
    private String validityperiod;//有效期
    private String approvalnumber;//批准文号
    private Integer merchantid;//所属商家编号
    private String producttype;//药品成品类型
    private String packtype;//药品包装类型
    private String Instructions;//说明书
    private Integer quantity;//药品数量
    private String stocktype;//药品库存状态
    private String portraitpath;//药品图片地址
    private Date addtime;//最近添加时间
    private Integer usetype;//药品使用类型
    private Integer packagingunit;//药品包装单位

    public Drug() {
    }

    public Drug(Integer quantity) {
        this.quantity = quantity;
    }

    public Drug(String stocktype) {
        this.stocktype = stocktype;
    }
}
