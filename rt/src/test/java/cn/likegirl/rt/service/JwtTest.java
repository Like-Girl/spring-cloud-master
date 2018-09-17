package cn.likegirl.rt.service;

import cn.likegirl.rt.utils.JwtUtil;
import cn.likegirl.rt.utils.KeyUtil;
import cn.likegirl.rt.utils.TemperatureUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.collections.MapUtils;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.security.Key;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Test
    public void test02(){
        String s = "常温(-5℃~12℃)";
        String reg = "^.*\\([^\\(\\-0-9]*?\\-*?(\\-?[0-9]+)[^\\(\\-0-9]*\\-*?(\\-?[0-9]+)[^\\(0-9]*\\)$";
        Pattern compile = Pattern.compile(reg);
        Matcher matcher = compile.matcher(s);
        System.out.println(matcher.matches());
        if(matcher.matches()){
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.groupCount());
        }
    }

    @Test
    public void test04(){
//        System.out.println(format("常温(5℃~20℃)",BigDecimal.valueOf(20.00),BigDecimal.valueOf(20.00)));
//        System.out.println(Pattern.matches("^\\([^\\)]*\\)&","(20.0℃~20.0℃)"));
        TemperatureUtils.Temp temp = TemperatureUtils.parse("常温");
        System.out.println(temp.toString());
    }

    @Test
    public void test05(){
        Time time = Time.valueOf("04:05:00");
        System.out.println(time.toString());
    }

    @Test
    public void test06(){
        for (int i = 0; i < 10; i++){
            System.out.println(KeyUtil.generatorUUID());
        }


    }

    @Test
    public void test07(){
        Map<String,Object> map = new HashMap<>();
        map.put("from",null);
        map.put("to",null);
        System.out.println(MapUtils.isEmpty(map));
    }

    @Test
    public void test08(){
        System.out.println(KeyUtil.generatorUUID());
    }

}
