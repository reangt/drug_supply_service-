package com.xz;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication

//@EnableCaching //开启缓存
@EnableCreateCacheAnnotation //jetcache开启缓存
public class DrugSupplyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrugSupplyServiceApplication.class, args);
    }

}
