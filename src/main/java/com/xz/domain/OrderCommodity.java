package com.xz.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ordercommodity")
public class OrderCommodity {
    private Integer id;//
    private Integer merchantid;//
    private Integer drugid;//
    private String merchantname;//
    private String drugname;//
    private Integer drugquantity;//
    private Double drugprice;//
    private String drugspecification;//
    private String drugportraitpath;//
    private Integer orderid;//
    private Integer status;//
    private String delivery_method;//

    public OrderCommodity() {
    }

    public OrderCommodity(Integer status, String delivery_method) {
        this.status = status;
        this.delivery_method = delivery_method;
    }

    public OrderCommodity(Integer status) {
        this.status = status;
    }
}
