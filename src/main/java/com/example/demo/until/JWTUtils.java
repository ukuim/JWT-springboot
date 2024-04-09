package com.example.demo.until;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    /**
     * 生成token  header.payload.singature
     * private static final String SING = "ABC";  其中ABC为秘钥，最终采用  哈希进行加密
     */
    private static final String SING = "e7aa09e6bddb8f41ddb0d0df048707e5853452ed4880c56a9b771167e7ab6eb7";

    public static String getToken(Map<String, String> map) {

        Calendar instance = Calendar.getInstance();
        // 默认7天过期
        instance.add(Calendar.DATE, 7);

        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        // payload
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        String token = builder.withExpiresAt(instance.getTime())  //指定令牌过期时间
                .sign(Algorithm.HMAC256(SING));  // sign
        return token;
    }

    /**
     * 验证token  合法性
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }

    /**
     * 获取token信息方法
     */
    public static DecodedJWT getTokenInfo(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        return verify;
    }
        public static String getUserName(String token) {
        try {

            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (Exception e) {
            return null;
        }
    }
}

