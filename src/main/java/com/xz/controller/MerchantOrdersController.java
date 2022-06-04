package com.xz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xz.controller.utils.JwtHelper;
import com.xz.controller.utils.Result;
import com.xz.controller.utils.ResultCodeEnum;
import com.xz.domain.MerchantOrders;
import com.xz.service.MerchantOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchantorders")
public class MerchantOrdersController {

    @Autowired
    private MerchantOrdersService merchantOrdersService;

    @PostMapping("/getall/{currentPage}/{pageSize}")
    public Result getallByid(@RequestHeader("token") String token, @PathVariable Integer currentPage, @PathVariable Integer pageSize){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        Long merchantid = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        if (userType!=2){
            return Result.fail(userType);
        }
        Page<MerchantOrders> page=new Page<>(currentPage,pageSize);
        IPage page1 = merchantOrdersService.getallByid(page, merchantid.intValue());
        return Result.stock(page1,merchantid.intValue());
    }
}
