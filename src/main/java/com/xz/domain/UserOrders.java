package com.xz.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("userorders")
public class UserOrders {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;//
    private Integer userid;//
    private Double total_amount;//
    private Integer status;//1：待付款;2:待收货;3:已完成;4:已取消
//    private String delivery_method;//
    private Date addtime;//
    private String receiver_name;//
    private String receiver_phone;//
    private String receiver_province;//
    private String receiver_city;//
    private String receiver_region;//
    private String receiver_detail_address;//
    private String note;//
    private String payment_time;//
    private String receive_time;//

    public UserOrders() {
    }

    public UserOrders(Integer status, String payment_time) {
        this.status = status;
        this.payment_time = payment_time;
    }

    public UserOrders(Integer status) {
        this.status = status;
    }

    public UserOrders(String receive_time) {
        this.receive_time = receive_time;
    }
}
