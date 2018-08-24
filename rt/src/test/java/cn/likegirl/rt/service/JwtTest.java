package cn.likegirl.rt.service;

import cn.likegirl.rt.utils.JwtUtil;
import cn.likegirl.rt.utils.TemperatureUtils;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.security.Key;
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

    public String format(String typeName, BigDecimal minTemp, BigDecimal maxTemp){
        if(StringUtils.isEmpty(typeName)
                || minTemp == null
                || maxTemp == null){
            throw new RuntimeException("参数错误");
        }
        typeName = typeName.replaceAll("(\\([^\\)]*\\))","");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(typeName)
                .append("(")
                .append(minTemp.toString())
                .append("℃~")
                .append(maxTemp.toString())
                .append("℃)");
        return stringBuilder.toString();
    }

    public static Map<String,Object> parse(String s){
        Map<String,Object> result = new HashMap<>();
        String regex = "^(.*)\\([^\\(\\-0-9]*?\\-*?(\\-?[0-9]+)[^\\(\\-0-9]*\\-*?(\\-?[0-9]+)[^\\(0-9]*\\)$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(s);
        if(matcher.matches()){
            result.put("typeTemp",matcher.group(1));
            result.put("minTemp",BigDecimal.valueOf(Double.valueOf(matcher.group(2))));
            result.put("maxTemp",BigDecimal.valueOf(Double.valueOf(matcher.group(3))));
        }
        return result;
    }

    @Test
    public void test04(){
//        System.out.println(format("常温(5℃~20℃)",BigDecimal.valueOf(20.00),BigDecimal.valueOf(20.00)));
//        System.out.println(Pattern.matches("^\\([^\\)]*\\)&","(20.0℃~20.0℃)"));
        TemperatureUtils.Temp temp = TemperatureUtils.parse("常温(20.00℃~20.00℃)");
        System.out.println(temp.toString());
    }

    @Test
    public void test05(){
        System.out.println("123".split(",").length);
    }
}
