package com.xz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.domain.Drug;
import com.xz.domain.QualificationReview;

public interface QRService extends IService<QualificationReview> {
    IPage getQRAll(Page<QualificationReview> page, int usertype);
}
