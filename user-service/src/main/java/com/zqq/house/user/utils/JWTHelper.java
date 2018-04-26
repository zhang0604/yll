package com.zqq.house.user.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;
import java.util.Map;

/**
 * Created By 张庆庆
 * DATA: 2018/4/13
 * TIME: 22:50
 */

public class JWTHelper {

    private static final String SECRET = "session_secret";
    private static final String ISSUSER = "house_user";

    /**
     * 生成 token
     *
     * @param claims-->这里的map 存储用户的一些核心信息
     * @return
     */
    public static String getToken(Map<String,String> claims){

        try {
            //声明 JWT算法
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            //设置过期时间 -->一旦设置无法更改(致命缺陷)
            JWTCreator.Builder builder = JWT.create().withIssuer(ISSUSER).withExpiresAt(DateUtils.addDays(new Date(),1));
            claims.forEach((k,v)->builder.withClaim(k,v));
            //设置签名信息
            return builder.sign(algorithm).toString();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    /**
     * 根据token 解析出 存入token中的用户核心信息
     * @param token
     * @return
     */
    public static Map<String,String> verifyToken(String token){
        try {

            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUSER).build();
            DecodedJWT jwt = verifier.verify(token);
            //获取 token中存储 map集合
            Map<String, Claim> claims = jwt.getClaims();
            Map<String,String> resultMap = Maps.newHashMap();
            claims.forEach((k,v) ->resultMap.put(k,v.asString()));
            return resultMap;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
