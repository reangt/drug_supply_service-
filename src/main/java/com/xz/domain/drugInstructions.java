package com.xz.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("druginstructions")
public class drugInstructions {
    private Integer id;//药品说明书编号
    private String Indicationsoffunction;//功能主治
    private String usage;//用法用量
    private String taboo;//禁忌
    private String Precautions;//注意事项
    private String Storage;//储藏条件

    public drugInstructions() {
    }
}
