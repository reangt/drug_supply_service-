package com.xz.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

@Data
@TableName("historicalprice")
public class HistoricalPrice {
    private Integer id;//商品历史价格表编号
    private Integer drugid;//药品编号
    private Double drugprice;//药品价格
    private DateTimeLiteralExpression.DateTime changetime;//变化时间
}
