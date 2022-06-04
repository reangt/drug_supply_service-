package com.xz.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.xz.controller.utils.GetNetworkTime;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/test")
public class Test {
//    public static void main()
    public static void main(String[] args) {
        int[] aa=new int[6];
        System.out.println(aa.length);
        System.out.println(aa[0]);
        System.out.println(aa);
        aa[0]=1;
        aa[1]=2;
        for (int i = 0; i < aa.length; i++) {
            if (aa[i]==0){
                break;
            }
            System.out.println(aa[i]);
        }

    }
}
