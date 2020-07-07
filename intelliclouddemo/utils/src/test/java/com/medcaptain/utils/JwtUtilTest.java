package com.medcaptain.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JwtUtilTest {

    @Test
    public void jwtTest() {
        String secret = "67f22881-f115-49a1-a694-65f695a6bd00";
        Map<String, Object> claims = new HashMap<>();
        claims.put("yzx", "67f22881-f115-49a1-a694-65f695a6bd01");
        claims.put("czb", "67f22881-f115-49a1-a694-65f695a6bd02");
        claims.put("lhy", "67f22881-f115-49a1-a694-65f695a6bd03");
        String jwtString = JwtUtil.generateToken(secret, claims);
        System.out.println(jwtString);
        Map<String, Object> jwtMap = JwtUtil.getClaims(secret, jwtString);
        System.out.println(jwtMap);
    }

}
