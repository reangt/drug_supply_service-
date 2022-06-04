package com.xz.controller.utils;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;


@Data
@Component
public class TokenUtils {
    private static String signature="fsdsf2dsaasdgaagf24srffsdsdfsdf1245effssdfsdf";
    public static String createToken(String username,Object claim,Long time){
        String jw= Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("user",JSON.toJSONString(claim))
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+time*30*1000))
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
        System.out.println(jw);
        return jw;
    }

    public  Claims getcoken(String jwt){
        try {
            return Jwts.parser().setSigningKey(signature).parseClaimsJws(jwt).getBody();
        } catch (UnsupportedJwtException e) {
            return null;
        }
    }

    public Boolean istokenExpired(Claims claims){
        return claims.getExpiration().before(new Date());
    }
    public static void main(String[] args) {
        System.out.println("生成token：");
        String token = createToken("ghgh", "a:1,j:2", 30L);
        System.out.println("解密:");
//        Claims getcoken = getcoken(token);
//        System.out.println(getcoken);
//        System.out.println(getcoken.get("user"));

    }
}
