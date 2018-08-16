package cn.likegirl.rt.service;

import cn.likegirl.rt.utils.JwtUtil;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.Test;

import java.security.Key;

public class JwtTest {

    @Test
    public void test01(){
        Key key = JwtUtil.generateKey(JwtUtil.JWT_ALG,JwtUtil.JWT_RULE);
        System.out.println(key);
        String sub = "1001";
        String token = JwtUtil.buildJWT(sub);
        System.out.println(token);
        System.out.println(token.startsWith(JwtUtil.JWT_SEPARATOR));

//        String authToken = token.substring(JwtUtil.JWT_SEPARATOR.length());
        Claims claims = JwtUtil.parseJWT(key,token).getBody();
        System.out.println(claims.getSubject());

    }
}
