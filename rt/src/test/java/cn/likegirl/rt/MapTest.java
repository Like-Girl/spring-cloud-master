package cn.likegirl.rt;

import cn.likegirl.rt.utils.BusinessMap;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MapTest {

    @Test
    public void test01(){

        BusinessMap<String,Object> body = new BusinessMap<>();
        body.put("time","11:23:11");
        body.put("datetime","2018-09-21 01:26:01");
        body.put("date","2018-09-21");
//        Long[] dates = {System.currentTimeMillis(),System.currentTimeMillis()};
        List<Long> dates = Arrays.asList(System.currentTimeMillis(),System.currentTimeMillis());
        body.put("timeC", dates);
        body.put("dec",12.12);

        int a = 123456789;
        body.put("intValue",a);
        body.put("longValue",123L);

        BusinessMap<String,Object> body1 = new BusinessMap<>();
        body1.put("datetime","2018-09-21 01:26:01");
        System.err.println(body.get("datetime1",Date.class,true));
        System.err.println(body1.get("datetime",Date.class));

        System.out.println(body.parseArray("timeC",Long.class));


        System.out.println(body.get("timeC", Date[].class).length);
        System.out.println(body.get("dec", BigDecimal.class));

    }

    @Test
    public void test02(){
        Random random = new Random();
        System.out.println(random.nextInt(100));
    }


    @Test
    public void test03(){
        String sid = "TMS:WEB_SOCKET:SESSION:236:0655a264-127f-4c25-85a5-d055705a879f";
        String rex = "^\\S+:([\\S]{36})$";
        Pattern compile = Pattern.compile(rex);
        Matcher matcher = compile.matcher(sid);
        if(matcher.matches()){
            System.out.println(matcher.groupCount());
            System.out.println(matcher.group(1));
        }
    }

    @Test
    public void test04(){
        Time t1 = Time.valueOf("09:23:00");
        Time t2 = Time.valueOf("09:23:00");
        System.out.println(t1.compareTo(t2));
    }

    @Test
    public void test05(){
        float a = 12344444444444444444444444444444444443.23000F;
        double b = 0.12;
        Float c = 0.12F;
    }
}
