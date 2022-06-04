package com.xz.controller.utils;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class Code {
    public String generator(String tele){
        int hash=tele.hashCode();
        int encryption=222006;
        long result = hash ^ encryption;
        long nowtime=System.currentTimeMillis();
        result=result ^ nowtime;
        long code=result % 1000000;
        code=code<0?-code:code;
        return code+"";
    }

    @Cacheable(value = "SMcode",key = "#tele")
    public String get(String tele){
        return null;
    }
    public static void main(String[] args) {
        System.out.printf(new Code().generator("12345678911"));
    }
}
