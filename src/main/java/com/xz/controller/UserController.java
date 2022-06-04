package com.xz.controller;

import com.xz.controller.utils.R;
import com.xz.controller.utils.TokenUtils;
import com.xz.domain.User;
import com.xz.service.UserService;
import io.jsonwebtoken.Claims;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @PostMapping("/zc")//注册用户
    public R save(@RequestBody User user){
        if (user.getName()!=null&&user.getPassword()!=null){
            //str.replaceAll("\\s*", ""); 可以替换大部分空白字符， 不限于空格
            // \\s* 可以匹配空格、制表符、换页符等空白字符的其中任意一个。
            String name= user.getName().replaceAll("\\s*","");
            String password=user.getPassword().replaceAll("\\s*","");

            Boolean flag1=!name.isEmpty();
            Boolean flag2=!password.isEmpty();
            if (flag1&&flag2){
                if (userService.getByname(name)==null){
                    return new R(userService.save(new User(name,password)));
                }else return new R("该名称已注册-_-!，请重新输入") ;

            }else if (!flag1&&!flag2){
                return new R("你输入的用户名和密码为空-_-!，请重新输入");
            }else if (flag1){
                return new R("你输入的密码为空-_-!，请重新输入");
            }return new R("你输入的用户名为空-_-!，请重新输入");
        }else return new R("未输入用户名或密码-_-!，请重新输入");
    }

    @GetMapping("/sc/{id}") //删除用户
    public Object delect(@PathVariable Integer id){
        return userService.removeById(id);
    }


    @PostMapping("/dl/{name}/{password}") //用户登录
    public R login(@PathVariable String name,@PathVariable String password){
        System.out.println("进入登录");
        if (name!=null&&password!=null){
            String loginName=name.replaceAll("\\s*","");
            String loginPassword=password.replaceAll("\\s*","");
            if(userService.getByNameAndPassword(loginName,loginPassword)!=null){
                //token令牌
                String token= TokenUtils.createToken(name,"这是内容",1L);
//                response.setHeader(tokenUtils.getcoken(jwt).getId(),jwt);
//                HttpSession session=r

//                stringRedisTemplate.opsForHash().put("token",name,token);
                return new R(true,token,"登录成功");
//                return new R(token);
            }else if (userService.getByname(loginName)!=null){
                return new R("登录失败!密码错误，请检查密码后重新输入");
            }else return new R("登录失败!没有此用户，请检查用户名后重新输入");
        }else return new R("未输入用户名或密码-_-!，请重新输入");
    }

    @PostMapping("/xg") //用户修改
    public Object update(@RequestBody User user){
        return null;
    }

//    private HttpServletResponse response;
//    private Token gentoken(String name){
//        String jwt= TokenUtils.createToken(name,null,7*24*60*60L);
//        response.setHeader(tokenUtils.getcoken(jwt).getId(),jwt);
////        token.set
//        return null;
//    }
    @PostMapping("/test")
    public R test(String token){
        Claims getcoken = tokenUtils.getcoken(token);
        if (getcoken!=null){
            return new R(true,getcoken);
        }else return new R(false);
    }
}
