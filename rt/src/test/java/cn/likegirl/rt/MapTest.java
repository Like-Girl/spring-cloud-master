package cn.likegirl.rt;

import cn.likegirl.rt.utils.BusinessMap;
import org.apache.commons.beanutils.ConvertUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;


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
}
