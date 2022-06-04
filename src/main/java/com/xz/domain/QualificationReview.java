package com.xz.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("qualificationreview")
public class QualificationReview {
    private Integer id;//
    private Integer usertype;//
    private Integer userid;//
    private String username;//
    private String certificatepath;//
    private Integer state;//

    public QualificationReview() {
    }

    public QualificationReview(String certificatepath) {
        this.certificatepath = certificatepath;
    }

    public QualificationReview(Integer usertype, Integer userid, String username, String certificatepath, Integer state) {
        this.usertype = usertype;
        this.userid = userid;
        this.username = username;
        this.certificatepath = certificatepath;
        this.state = state;
    }
}
