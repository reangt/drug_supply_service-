package com.xz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xz.domain.QualificationReview;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QRDao extends BaseMapper<QualificationReview> {
}
