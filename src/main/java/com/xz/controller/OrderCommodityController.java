package com.xz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xz.controller.utils.Result;
import com.xz.domain.MerchantOrders;
import com.xz.domain.OrderCommodity;
import com.xz.service.MerchantOrdersService;
import com.xz.service.OrderCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordercommodity")
public class OrderCommodityController {

    @Autowired
    private OrderCommodityService orderCommodityService;
    @Autowired
    private MerchantOrdersService merchantOrdersService;

    @PostMapping("/getall/{currentPage}/{pageSize}/{orderid}")
    public Result getall(@PathVariable Integer currentPage,@PathVariable Integer pageSize,@PathVariable Integer orderid){

        Page<OrderCommodity> page=new Page<>(currentPage,pageSize);
        IPage page1 = orderCommodityService.getallByid(page, orderid);
        return Result.ok(page1);
    }

    @PostMapping("/getallBym/{currentPage}/{pageSize}/{merchantid}/{orderid}")
    public Result getallBym(@PathVariable Integer currentPage,@PathVariable Integer pageSize,@PathVariable Integer merchantid,@PathVariable Integer orderid){

        Page<OrderCommodity> page=new Page<>(currentPage,pageSize);
        IPage page1 = orderCommodityService.getallByidm(page, merchantid,orderid);
        return Result.ok(page1);
    }

    @PostMapping("/ship/{id}")
    public Result toship(@PathVariable Integer id, @RequestBody String delivery_method){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",id);
        boolean update = orderCommodityService.update(new OrderCommodity(2, delivery_method), queryWrapper);
        if (!update){
            return Result.fail("发货失败");
        }
        OrderCommodity orderCommodity = orderCommodityService.getById(id);
        QueryWrapper queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("merchantid",orderCommodity.getMerchantid());
        queryWrapper1.eq("orderid",orderCommodity.getOrderid());
        boolean update1 = merchantOrdersService.update(new MerchantOrders(2), queryWrapper1);
        if (!update1){
            return Result.fail("商家订单状态修改失败");
        }
        return Result.ok();
    }
}
