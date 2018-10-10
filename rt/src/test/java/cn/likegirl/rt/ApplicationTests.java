package cn.likegirl.rt;

import cn.likegirl.rt.config.rabbitmq.Sender;
import cn.likegirl.rt.utils.BusinessMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {


    @Autowired
    private Sender sender;

    @Test
    public void contextLoads() {
        BusinessMap<String, Object> body = new BusinessMap<>();
        body.put("type", "温控通知");
        body.put("message", "温度过低");
        for (int i = 0; i < 5; i++){
            body.put("routingKey", UUID.randomUUID().toString());
            sender.send(body);
        }

        try {
            Thread.sleep(60*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
