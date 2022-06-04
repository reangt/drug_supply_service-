package com.xz.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("daugtype")
public class DrugType {
    private Integer id;//药品类型编号
    private String producttype;//药品成品类型
    private String packtype;//药品包装类型
    private String usetype;//药品使用类型
    private String packagingunit;//药品包装单位

    public DrugType() {
    }
}
