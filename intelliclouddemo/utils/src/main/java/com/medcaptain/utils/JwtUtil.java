package com.medcaptain.utils;

import io.jsonwebtoken.*;

import java.util.Map;

/**
 * @author : yangzhixiong
 * @description : JWT工具类，利用JWT生成Token
 * @date : 2018/9/27
 */
public class JwtUtil {

    /**
     *
     * @param secret 密钥
     * @param claims
     * @return
     */
    public static String generateToken(String secret, Map<String, Object> claims){

        String compactJws = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return compactJws;
    }

    /**
     *
     * @param secret 密钥
     * @param token
     * @return
     */
    public static Map getClaims(String secret, String token){

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return claims.getBody();
        } catch (MissingClaimException e) {
            e.printStackTrace();
            // we get here if the required claim is not present
        } catch (IncorrectClaimException e) {
            e.printStackTrace();
            // we get here if the required claim has the wrong value
        }catch (Exception e){
            System.out.println("错误");
        }
        return null;
    }

}
