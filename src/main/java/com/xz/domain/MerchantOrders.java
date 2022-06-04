package com.xz.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("merchantorders")
public class MerchantOrders {
    private Integer id;//
    private Integer merchantid;//
    private Integer orderid;//
    private Double total_amount;//
    private Integer status;//1：待发货;2:待签收;3:已完成;4:已取消
    private Date addtime;//
    private String receiver_name;//
    private String receiver_phone;//
    private String receiver_province;//
    private String receiver_city;//
    private String receiver_region;//
    private String receiver_detail_address;//
    private String note;//
    private String receive_time;//

    public MerchantOrders() {
    }

    public MerchantOrders(Integer status) {
        this.status = status;
    }

    public MerchantOrders(Integer status, String receive_time) {
        this.status = status;
        this.receive_time = receive_time;
    }
}
