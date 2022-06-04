package com.xz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xz.controller.utils.JwtHelper;
import com.xz.controller.utils.Result;
import com.xz.controller.utils.ResultCodeEnum;
import com.xz.domain.Drug;
import com.xz.domain.ShoppingCart;
import com.xz.service.ShoppingCartService;
import io.swagger.annotations.ResponseHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService scs;

    @PostMapping("/add")
    public Result addcart(@RequestBody ShoppingCart shoppingCart){
        Integer byDrugUserid = scs.getByDrugUserid(shoppingCart.getUserid(), shoppingCart.getDrugid());
        if (byDrugUserid!=null){
            return Result.fail("您已添加该商品");
        }
        boolean save = scs.save(shoppingCart);
        return Result.ok(save);
    }

    @PostMapping("/getall/{currentPage}/{pageSize}")
    public Result getallByid(@RequestHeader("token") String token,@PathVariable Integer currentPage,@PathVariable Integer pageSize){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        Long userid = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        if (userType!=1){
//            System.out.println("----------");
            return Result.fail(userType);
        }
        Page<ShoppingCart> page=new Page<>(currentPage,pageSize);
            IPage page1 = scs.getallByid(page, userid.intValue());
        return Result.stock(page1,userid.intValue());
    }
}
