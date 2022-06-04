package com.xz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.dao.QRDao;
import com.xz.domain.QualificationReview;
import com.xz.service.QRService;
import org.springframework.stereotype.Service;

@Service
public class QRServiceImpl extends ServiceImpl<QRDao, QualificationReview> implements QRService {
    @Override
    public IPage getQRAll(Page<QualificationReview> page, int usertype) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("usertype",usertype);
        queryWrapper.orderByDesc("id");
        Page QRpage = baseMapper.selectPage(page, queryWrapper);
        return QRpage;
    }
}
