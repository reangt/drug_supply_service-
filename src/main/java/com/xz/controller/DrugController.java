package com.xz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xz.controller.utils.JwtHelper;
import com.xz.controller.utils.R;
import com.xz.controller.utils.Result;
import com.xz.controller.utils.ResultCodeEnum;
import com.xz.domain.Drug;
import com.xz.domain.ShoppingCart;
import com.xz.service.DrugService;
import com.xz.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drugs")
public class DrugController {
    @Autowired
    private DrugService drugService;
    @Autowired
    private ShoppingCartService scs;

    @PostMapping("/uploadimag")
    public Result uploadImage(@RequestBody Drug drug) {
        if (drug.getId()!=null){
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("id",drug.getId());
            System.out.println("到这了");
            boolean update = drugService.update(drug, queryWrapper);
            //更新购物车药品图片
            QueryWrapper queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("drugid",drug.getId());
            scs.update(new ShoppingCart(drug.getPortraitpath()),queryWrapper1);
            return Result.ok(update);
        }

        return Result.fail();
    }

    @PostMapping("/add")//添加商品
    public Result save(@RequestBody Drug drug) {
        if (drug.getId()!=null){
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("id",drug.getId());
            boolean update = drugService.update(drug, queryWrapper);
            //更新购物车药品信息
            QueryWrapper queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("drugid",drug.getId());
            ShoppingCart shoppingCart = new ShoppingCart(drug.getName(), drug.getPrice(), drug.getSpecification(), drug.getStocktype(),drug.getQuantity());
            scs.update(shoppingCart,queryWrapper1);
            return Result.ok(update);
        }
        boolean save = drugService.save(drug);
        return Result.ok(save);
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id){
        return Result.ok(drugService.removeById(id));
    }

    @GetMapping("/byid/{id}")
    public Result getByid(@PathVariable Integer id){
        return Result.ok(drugService.getByid(id));
    }

    //查询商家药品库存
    @PostMapping("/drugstocks/{currentPage}/{pageSize}")
    public Result getstock(@RequestHeader("token") String token,@PathVariable Integer currentPage,@PathVariable Integer pageSize,@RequestBody Drug drug){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        Long merchantid = JwtHelper.getUserId(token);
//        System.out.println("这里没事");
//        if (!drug.getId().equals("")){
//            System.out.println("查id");
//            Drug byid = drugService.getByid(drug.getId());
//            return Result.ok(byid);
//        }
//        if (!drug.getName().equals("")){
//            System.out.println("查name");
//            Drug byname = drugService.getByname(drug.getName(), merchantid);
//            return Result.ok(byname);
//        }
//        System.out.println("只执行分页");
        Page<Drug> page=new Page<>(currentPage,pageSize);
        IPage pages= drugService.getDrugAllBymerchant(page,merchantid.intValue(),drug.getId(),drug.getName());

        return Result.stock(pages,merchantid.intValue());
    }

    //查询所有药品
    @PostMapping("/all/{currentPage}/{pageSize}")
    public Result getDrugAll(@PathVariable Integer currentPage,@PathVariable Integer pageSize,@RequestBody Drug drug){
        Page<Drug> page=new Page<>(currentPage,pageSize);
        IPage pages= drugService.getDrugAll(page,drug.getId(),drug.getName());

        return Result.ok(pages);
    }
}
