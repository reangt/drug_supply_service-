package com.xz.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("shoppingcart")
public class ShoppingCart {
    private Integer id;//
    private Integer userid;//用户编号
    private Integer drugid;//药品id
    private String name;//药品名称
    private Double price;//药品价格
    private String specification;//药品规格
    private Integer quantity;//添加数量
    private String portraitpath;//药品图片
    private String merchantname;//药品所属商家名称
    private String fettle;//药品状态
    private Date addtime;//药品添加时间
    private Integer merchantid;//药品所属商家id
    private Integer maxquantity;//药品所属商家id

    public ShoppingCart(String portraitpath) {
        this.portraitpath = portraitpath;
    }

    public ShoppingCart(String name, Double price, String specification, String fettle,Integer maxquantity) {
        this.name = name;
        this.price = price;
        this.specification = specification;
        this.fettle = fettle;
        this.maxquantity=maxquantity;
    }

    public ShoppingCart() {
    }

    public ShoppingCart(Integer maxquantity) {
        this.maxquantity = maxquantity;
    }

    public ShoppingCart(Integer quantity, String fettle) {
        this.quantity = quantity;
        this.fettle = fettle;
    }
}
