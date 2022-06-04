package com.xz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xz.controller.utils.JwtHelper;
import com.xz.controller.utils.R;
import com.xz.controller.utils.Result;
import com.xz.controller.utils.ResultCodeEnum;
import com.xz.domain.Drug;
import com.xz.domain.QualificationReview;
import com.xz.domain.ShoppingCart;
import com.xz.domain.UserMerchant;
import com.xz.service.QRService;
import com.xz.service.ShoppingCartService;
import com.xz.service.UserMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usermarchants")
public class UserMarchantController {
    @Autowired
    private UserMerchantService ums;
    @Autowired
    private ShoppingCartService scs;
    @Autowired
    private QRService qrService;

    @PostMapping("/zc")
    public R save(@RequestBody UserMerchant userMerchant){
//        if (userMerchant.getName()!=null&&userMerchant.getPassword()!=null){
//            String name=userMerchant.getName().replaceAll("\\s*","");
//            String password=userMerchant.getPassword().replaceAll("\\s*","");
//            if (!name.isEmpty()&&!password.isEmpty()){
//                if (ums.getByname(name,userMerchant.getType())==null){
//                    return new R(ums.save(new UserMerchant(name,password,userMerchant.getType())));
//                }else return new R(name+"已存在，请重新输入");
//
//            }return new R("输入的用户名或密码为空-_-!，请重新输入");
//        }else return new R("未输入用户名或密码-_-!，请重新输入");
        return new R(false);
    }

//    @PostMapping("/dl/{name}/{password}/{type}") //用户登录
//    public R login(@PathVariable String name,@PathVariable String password,@PathVariable Integer type){
//        if (name!=null&&password!=null){
//            String loginName=name.replaceAll("\\s*","");
//            String loginPassword=password.replaceAll("\\s*","");
//            if(ums.getByNameAndPassword(loginName,loginPassword,type)!=null){
//                return new R(true,"登录成功");
//            }else if (ums.getByname(loginName,type)!=null){
//                return new R("登录失败!密码错误，请检查密码后重新输入");
//            }else return new R("登录失败!没有此用户，请检查用户名后重新输入");
//        }else return new R("未输入用户名或密码-_-!，请重新输入");
//    }

    @GetMapping("/cx/{id}")
    public Result Inquire(@PathVariable Integer id){
        return Result.ok(ums.getNameById(id));
    }

    @GetMapping("/sc/{id}") //删除用户
    public R delect(@PathVariable Integer id){
        return new R(ums.removeById(id));
    }

    @PostMapping("/updata")
    public Result updata(@RequestHeader("token") String token,@RequestBody UserMerchant userMerchant){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        Long userId = JwtHelper.getUserId(token);
//        Integer userType = JwtHelper.getUserType(token);
        if (userMerchant.getId().longValue()!=userId){
            return Result.fail().message("提交的用户与本地用户不一致");
        }
        UpdateWrapper<UserMerchant> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",userId);
        boolean update = ums.update(userMerchant, updateWrapper);
        //更新购物车
        scs.replaceMNameByid(userMerchant.getId(),userMerchant.getName());
        return Result.ok(update);
    }

    @PostMapping("/uploadprove")
    public Result uploadprove(@RequestBody UserMerchant userMerchant) {
        if (userMerchant.getId()!=null){
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("id",userMerchant.getId());
            System.out.println("到这了");
            boolean update = ums.update(userMerchant, queryWrapper);
            //更新资质表
            QueryWrapper queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("usertype",2);
            queryWrapper1.eq("userid",userMerchant.getId());
//            scs.update(new ShoppingCart(userMerchant.getPortraitpath()),queryWrapper1);
            boolean update1 = qrService.update(new QualificationReview(userMerchant.getCertificatepath()), queryWrapper1);
            if (!update1){
                System.out.println("---------------------新增中------------------");
                qrService.save(new QualificationReview(2,userMerchant.getId(),userMerchant.getName(),userMerchant.getCertificatepath(),1));
            }
            return Result.ok(update);
        }

        return Result.fail();
    }
}
