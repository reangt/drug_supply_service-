package com.xz.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonArray;
import com.xz.controller.utils.GetNetworkTime;
import com.xz.controller.utils.JwtHelper;
import com.xz.controller.utils.Result;
import com.xz.controller.utils.ResultCodeEnum;
import com.xz.dao.OrderCommodityDao;
import com.xz.dao.UserOrdersDao;
import com.xz.domain.*;
import com.xz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class UserOrdersController {

    @Autowired
    private UserOrdersService userOrdersService;
    @Autowired
    private UserOrdersDao userOrdersDao;
    @Autowired
    private OrderCommodityService orderCommodityService;
    @Autowired
    private OrderCommodityDao orderCommodity;
    @Autowired
    private DrugService drugService;
    @Autowired
    private ShoppingCartService scs;
    @Autowired
    private MerchantOrdersService merchantOrdersService;

    @PostMapping("/add")
    public Result addorders(@RequestBody OrdersCart ordersCart){

//        System.out.println("------------");
//        System.out.println(ordersCart.getUserOrders());
//        System.out.println(ordersCart.getShoppingCarts());
//        System.out.println("--------------");
//        System.out.println(ordersCart.getShoppingCarts().size());
//        System.out.println(ordersCart.getShoppingCarts().get(0));
        //获取生成用户订单的参数
        UserOrders userOrders = ordersCart.getUserOrders();
        ShoppingCart shoppingCart = ordersCart.getShoppingCarts().get(0);
        //获取userid
        userOrders.setUserid(shoppingCart.getUserid());
        //获取订单总金额
        Double sum=0.00;
        for (int i = 0; i < ordersCart.getShoppingCarts().size(); i++) {
            sum += (ordersCart.getShoppingCarts().get(i).getPrice()) * (ordersCart.getShoppingCarts().get(i).getQuantity());
        }
        userOrders.setTotal_amount(sum);
        //修改订单状态为待付款：1
        userOrders.setStatus(1);
        //生成用户订单
        int insert = userOrdersDao.insert(userOrders);
        if (insert!=1){
            return Result.fail("订单提交失败");
        }
//        System.out.println("总金额："+sum);
//        System.out.println("订单id："+userOrders.getId());
//        System.out.println("购物车："+ordersCart.getShoppingCarts());
        //生成订单明细表
        for (int i = 0; i < ordersCart.getShoppingCarts().size(); i++) {
            //生成订单明细表
            OrderCommodity orderCommodity=new OrderCommodity();
            //获取订单id
            orderCommodity.setOrderid(userOrders.getId());
            //获取商家编号
            orderCommodity.setMerchantid(ordersCart.getShoppingCarts().get(i).getMerchantid());
            //获取药品编号
            orderCommodity.setDrugid(ordersCart.getShoppingCarts().get(i).getDrugid());
            //获取商家名称
            orderCommodity.setMerchantname(ordersCart.getShoppingCarts().get(i).getMerchantname());
            //获取药品名称
            orderCommodity.setDrugname(ordersCart.getShoppingCarts().get(i).getName());
            //获取药品数量
            orderCommodity.setDrugquantity(ordersCart.getShoppingCarts().get(i).getQuantity());
            //获取药品单价
            orderCommodity.setDrugprice(ordersCart.getShoppingCarts().get(i).getPrice());
            //获取药品规格
            orderCommodity.setDrugspecification(ordersCart.getShoppingCarts().get(i).getSpecification());
            //获取药品图片
            orderCommodity.setDrugportraitpath(ordersCart.getShoppingCarts().get(i).getPortraitpath());
            //设置订单明细表状态为1：待发货
            orderCommodity.setStatus(1);
            //生成订单明细表
            boolean save = orderCommodityService.save(orderCommodity);
            if (!save){
                //删除用户订单
                userOrdersService.removeById(userOrders.getId());
                return Result.fail("订单商品表生成失败");
            }
        }
        //更新商家药品库存
        for (int i = 0; i < ordersCart.getShoppingCarts().size(); i++) {
            //获取药品更新库存
            int quantity = ordersCart.getShoppingCarts().get(i).getMaxquantity() - ordersCart.getShoppingCarts().get(i).getQuantity();
            //更新药品
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("id",ordersCart.getShoppingCarts().get(i).getDrugid());
            boolean update = drugService.update(new Drug(quantity), queryWrapper);
            if (!update){
                return Result.fail("药品库存更新失败");
            }
            //更新购物车药品信息
            QueryWrapper queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("drugid",ordersCart.getShoppingCarts().get(i).getDrugid());
            boolean update1 = scs.update(new ShoppingCart(quantity), queryWrapper1);
            if (!update1){
                return Result.fail("购物车药品库存更新失败");
            }
            //更新药品状态
            if (quantity==0){
                boolean update2 = drugService.update(new Drug(String.valueOf(false)), queryWrapper);
                if (!update2){
                    return Result.fail("药品状态更新失败");
                }
                boolean update3 = scs.update(new ShoppingCart(0, String.valueOf(false)), queryWrapper1);
                if (!update3){
                    return Result.fail("购物车药品状态更新失败");
                }
            }
        }
        return Result.ok();
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
            return Result.fail(userType);
        }
        Page<UserOrders> page=new Page<>(currentPage,pageSize);
        IPage page1 = userOrdersService.getallByid(page, userid.intValue());
        return Result.stock(page1,userid.intValue());
    }

    @PostMapping("/pays/{id}")
    public Result paySuccess(@PathVariable Integer id){
        //将用户订单状态改为2：待收货,更新支付时间
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",id);
        boolean update = userOrdersService.update(new UserOrders(2,GetNetworkTime.getNetworkTime()), queryWrapper);
        if (!update){
            return Result.fail("用户订单状态更新失败");
        }
        //商家订单生成
        //从用户订单表获取信息
        UserOrders userorders = userOrdersService.getById(id);
        //从订单商品表获取信息
        QueryWrapper queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("orderid",id);
        List<OrderCommodity> list = orderCommodity.selectList(queryWrapper1);
        System.out.println(list);
        int[] merchantid=new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            for (int k = 0; k < merchantid.length; k++) {
                if (merchantid[k]==0){
                    merchantid[k]=list.get(i).getMerchantid();
                    break;
                }
                if (merchantid[k]==list.get(i).getMerchantid()){
                    break;
                }
            }
        }
        for (int j = 0; j < merchantid.length; j++) {
            if (merchantid[j]==0){
                break;
            }
            //生成商家订单
            MerchantOrders merchantOrders = new MerchantOrders();
            merchantOrders.setMerchantid(merchantid[j]);
            merchantOrders.setOrderid(id);
            merchantOrders.setStatus(1);
            merchantOrders.setReceiver_name(userorders.getReceiver_name());
            merchantOrders.setReceiver_phone(userorders.getReceiver_phone());
            merchantOrders.setReceiver_province(userorders.getReceiver_province());
            merchantOrders.setReceiver_city(userorders.getReceiver_city());
            merchantOrders.setReceiver_region(userorders.getReceiver_region());
            merchantOrders.setReceiver_detail_address(userorders.getReceiver_detail_address());
            merchantOrders.setNote(userorders.getNote());
            boolean save = merchantOrdersService.save(merchantOrders);
            if (!save){
                return Result.fail("商家订单生成失败");
            }
        }
        return Result.ok();
    }

    @PostMapping("/receipt/{id}")
    public Result toReceipt(@PathVariable Integer id){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",id);

        //更新用户订单表
        userOrdersService.update(new UserOrders(3),queryWrapper);
        userOrdersService.update(new UserOrders(GetNetworkTime.getNetworkTime()),queryWrapper);
        //更新订单商品表
        QueryWrapper queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("orderid",id);
        orderCommodity.update(new OrderCommodity(3),queryWrapper1);
        //更新商家商品表
        merchantOrdersService.update(new MerchantOrders(3,GetNetworkTime.getNetworkTime()),queryWrapper1);
        return Result.ok();
    }
}
