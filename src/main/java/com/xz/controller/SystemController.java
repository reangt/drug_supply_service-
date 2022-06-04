package com.xz.controller;

import com.xz.controller.utils.*;
import com.xz.dao.DrugTypeDao;
import com.xz.domain.DrugType;
import com.xz.domain.LoginForm;
import com.xz.domain.User;
import com.xz.domain.UserMerchant;
import com.xz.service.DrugTypeService;
import com.xz.service.OssUploadService;
import com.xz.service.UserMerchantService;
import com.xz.service.UserService;
import com.xz.service.impl.UploadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMerchantService userMerchantService;
    @Autowired
    private DrugTypeService drugTypeService;
    @Autowired
    private UploadServiceImpl uploadService;
    @Autowired
    private OssUploadService ossUploadService;

    //文件上传
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile,HttpServletRequest request){

        if (multipartFile.isEmpty()){
            return "文件为空";
        }
        String dir=request.getParameter("dir");
//        return uploadService.uploadImg(multipartFile,dir);
        return ossUploadService.uploadfile(multipartFile,dir);

    }

    //获取药品类型
    @GetMapping("/gettype")
    public Result getDrugType(){
        List<Map<Integer,String>> getproducttype = drugTypeService.getproducttype();
        List<Map<Integer,String>> getpacktype = drugTypeService.getpacktype();
        List<Map<Integer,String>> getusetype = drugTypeService.getusetype();
        List<Map<Integer,String>> getpackagingunit = drugTypeService.getpackagingunit();
        Map<String,Object> map=new LinkedHashMap<>();
        map.put("producttype",getproducttype);
        map.put("packtype",getpacktype);
        map.put("usetype",getusetype);
        map.put("packagingunit",getpackagingunit);
        return Result.ok(map);
    }


    //用户注册请求
    @PostMapping("/register")
    public Result register(@RequestBody LoginForm loginForm){
        Object integer="sadf";
        switch (loginForm.getUserType()){
            case 1:
                String byname = userService.getByname(loginForm.getUsername());
                if (byname!=null){
                    return Result.fail().message("该用户已存在");
                }
                integer = userService.saveBynameBypassword(loginForm.getUsername(), loginForm.getPassword());
                break;
            case 2:
                String byname1 = userMerchantService.getByname(loginForm.getUsername());
                if (byname1!=null){
                    return Result.fail().message("该用户已存在");
                }
                integer = userMerchantService.saveBynameBypassword(loginForm.getUsername(), loginForm.getPassword());
                break;
        }
//        System.out.println(loginForm.getUsername()+loginForm.getPassword());
//        System.out.println(integer);
        return Result.ok(integer);
    }

    //验证是否为用户本人操作
    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader("token") String token) throws UnsupportedEncodingException {
//        java.net.URLDecoder.decode(token,"UTF-8");
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //解析token
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        Map<String,Object> map=new LinkedHashMap<>();
        switch (userType){
            case 1:
                User user = userService.getById(userId);
                map.put("userType",userType);
                map.put("user",user);
                break;
            case 2:
                UserMerchant userMerchant = userMerchantService.getById(userId);
                map.put("userType",userType);
                map.put("user",userMerchant);
                break;
        }

        return Result.ok(map);
    }

    //用户登录请求
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm,HttpServletRequest request){
        //验证码校验
        HttpSession session=request.getSession();
        String sessionverifiCode = (String)session.getAttribute("verifiCode");
        String loginverifiCode=loginForm.getVerifiCode();
        if ("".equals(sessionverifiCode)|| null==sessionverifiCode){
            return Result.fail().message("验证码失效");
        }
        if (!sessionverifiCode.equals(loginverifiCode)) {
            return Result.fail().message("验证码错误");
        }
        //移除验证码
        session.removeAttribute("verifiCode");
        //按用户类型校验
        Map<String,Object> map=new LinkedHashMap<>();
        switch (loginForm.getUserType()) {
            case 1:
                try {
                    User user=userService.login(loginForm);
                    if (user!=null) {
                        //用户类型+id转换成token发送
                        String token = JwtHelper.createToken(user.getId().longValue(), 1);
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                try {
                    UserMerchant user=userMerchantService.login(loginForm);
                    if (user!=null) {
                        //用户类型+id转换成token发送
                        String token = JwtHelper.createToken(user.getId().longValue(), 2);
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    if (loginForm.getUsername().equals("admin")&&loginForm.getPassword().equals("admin")) {
                        //用户类型+id转换成token发送
                        String token = JwtHelper.createToken((long)11, 3);
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("管理员用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("没有该用户");
    }

    //获取验证码请求
    @GetMapping("/getImage")
    public Object getImage(HttpServletRequest request, HttpServletResponse response){
        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片验证码并从char【】转换成String
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //从request拿到session
//        System.out.println("request："+request);
        HttpSession session = request.getSession();
        //放到session作用域
        session.setAttribute("verifiCode",verifiCode);
        //通过io流发送到浏览器
        try {
            ImageIO.write(verifiCodeImage,"JEPG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return verifiCode;
    }
}
