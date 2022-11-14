package com.alexzhli.bilibili.service.util;

import com.alexzhli.bilibili.domain.exception.ConditionException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;

public class TokenUtil {

    private static final String ISSUER = "alexzhli";

    // 创建用户令牌的方法
    public static String generateToken(Long userId) throws Exception {
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 60 * 60 * 7);
        // 存储用户id
        return JWT.create().withKeyId(String.valueOf(userId))
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime()) // 过期时间
                .sign(algorithm); // jwt签发

    }

    // 验证用户令牌的方法
    public static Long verifyToken(String token) {
        // 与生成令牌不一样，这里做额外的处理，而不是返回一个错误信息，主要在于对于用户体验的把控
        try {
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
            // 验证方法
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt =  verifier.verify(token);
            String userId = jwt.getKeyId();
            return Long.valueOf(userId);
        } catch (TokenExpiredException e) {
            // token过期的异常，用编号进行标识
            throw new ConditionException("555", "token过期！");
        } catch (Exception e) {
            // 通用的异常
            throw new ConditionException("非法用户token");
        }
    }

    // 生成一个refreshToken，只是有效期比较长
    public static String generateRefreshToken(Long userId) throws Exception {
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        // 存储用户id
        return JWT.create().withKeyId(String.valueOf(userId))
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime()) // 过期时间
                .sign(algorithm); // jwt签发

    }
}
