package com.xz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xz.controller.utils.Result;
import com.xz.domain.QualificationReview;
import com.xz.service.QRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qrs")
public class QRController {

    @Autowired
    private QRService qrService;

    //查询所有药品
    @PostMapping("/all2/{currentPage}/{pageSize}")
    public Result getDrugAll(@PathVariable Integer currentPage, @PathVariable Integer pageSize){
        Page<QualificationReview> page=new Page<>(currentPage,pageSize);
        IPage pages= qrService.getQRAll(page,2);

        return Result.ok(pages);
    }
}
