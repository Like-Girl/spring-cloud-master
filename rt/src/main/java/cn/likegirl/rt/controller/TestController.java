package cn.likegirl.rt.controller;

import cn.likegirl.rt.config.websocket.IRedisSessionService;
import cn.likegirl.rt.config.websocket.SocketMessage;
import cn.likegirl.rt.framework.exception.ParameterInvalidException;
import cn.likegirl.rt.framework.response.PlatformResult;
import cn.likegirl.rt.service.feign.OrderFeign;
import cn.likegirl.rt.utils.BusinessMap;
import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "error", commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "40")
    }, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "1"),
            @HystrixProperty(name = "maxQueueSize", value = "10"),
            @HystrixProperty(name = "keepAliveTimeMinutes", value = "1000"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "8"),
            @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
    })
    @RequestMapping(value = "/dept/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public PlatformResult hello(@PathVariable Long id) {
        if (id > 50) {
            throw new ParameterInvalidException();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("userId", 242);
        params.add("projectId", 352);
        params.add("assignTime", "2018-09-27 10:24:12");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
//        try {
//            Thread.sleep(10 * 1000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        ResponseEntity<PlatformResult> response = restTemplate.exchange("http://tms-mgr/applet/order/fixed-vehicle", HttpMethod.POST,requestEntity,PlatformResult.class);
        PlatformResult result = restTemplate.postForObject("http://tms-mgr-service/applet/order/fixed-vehicle", requestEntity, PlatformResult.class);
        return result;
    }

    public PlatformResult error(Long id) {
        return PlatformResult.failure(id + ",我被降级了");
    }

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private IRedisSessionService redisSessionService;

    /**
     * 向执行用户发送请求
     */
    @RequestMapping(value = "send2user")
    @ResponseBody
    public int sendMq2User(@RequestBody BusinessMap<String, Object> params) {
        // 根据用户名称获取用户对应的session id值
        String name = params.get("name", String.class, Boolean.TRUE);
        String wsSessionId = redisSessionService.get(name);

        SocketMessage message = new SocketMessage();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.setDate(df.format(new Date()));
        message.setMessage(params.get("msg", String.class, Boolean.TRUE));

        // 生成路由键值，生成规则如下: websocket订阅的目的地 + "-user" + websocket的sessionId值。生成值类似:
        String actualDestination = params.get("actualDestination", String.class);
        String routingKey = getTopicRoutingKey(actualDestination, wsSessionId) + params.get("t", String.class);
        // 向amq.topi交换机发送消息，路由键为routingKey
        LOGGER.info("向用户[{}]sessionId=[{}]，发送消息[{}]，路由键[{}]", name, wsSessionId, wsSessionId, routingKey);
        String exchanges = params.get("exchange", String.class);
        amqpTemplate.convertAndSend(exchanges, routingKey, JSONObject.toJSONString(message));
        return 0;
    }

    /**
     * 获取Topic的生成的路由键
     */
    private String getTopicRoutingKey(String actualDestination, String sessionId) {
        return actualDestination + "-user" + sessionId;
    }

    /**
     * 向执行用户发送请求
     */
    @RequestMapping(value = "send1user")
    @ResponseBody
    public int sendMq1User(@RequestBody BusinessMap<String, Object> params) {
        SocketMessage message = new SocketMessage();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.setDate(df.format(new Date()));
        message.setMessage(params.get("msg", String.class, Boolean.TRUE));
        String routingKey = params.get("routingKey", String.class, Boolean.TRUE);
        // 向amq.topi交换机发送消息，路由键为routingKey
        LOGGER.info("发送消息[{}]，路由键[{}]", params, routingKey);
        String exchange = params.get("exchange", String.class, Boolean.TRUE);
        amqpTemplate.convertAndSend(exchange, routingKey, JSONObject.toJSONString(message));
        return 0;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public PlatformResult hello1(@RequestBody Map<String, Object> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("actualDestination", "message");
        params.add("exchange", "amq.topic");
        params.add("userId", 191);
        params.add("voiceMessage", "您有新温度报警，请及时处理！");
        Map<String, Object> responseMessage = new HashMap<>();
        responseMessage.put("plateNumber", "沪DJ7232");
        responseMessage.put("temperature", "5℃");
        responseMessage.put("time", "2018-10-19 15:34:20");
        responseMessage.put("tempCtrol", "冷藏(-5℃~0℃)");
        params.add("responseMessage", responseMessage);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
//        try {
//            Thread.sleep(10 * 1000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        ResponseEntity<PlatformResult> response = restTemplate.exchange("http://tms-mgr/applet/order/fixed-vehicle", HttpMethod.POST,requestEntity,PlatformResult.class);
        PlatformResult result = restTemplate.postForObject("http://tms-mgr/test/sendTemperatureAlarmToUser", requestEntity, PlatformResult.class);
        return result;
    }


    @Autowired
    OrderFeign orderFeign;

    @RequestMapping(value = "/site")
    @ResponseBody
    public PlatformResult test10() {
        PlatformResult result = orderFeign.moduleSiteListByLine(333L, null);
//        PlatformResult result = restTemplate.getForObject("http://tms-mgr/applet/site/list/333", PlatformResult.class);
        return result;
    }

    @RequestMapping(value = "/vehicles")
    @ResponseBody
    public PlatformResult test11() {
//        PlatformResult result = orderFeign.moduleSiteListByLine(333L, null);
//        PlatformResult result = orderFeign.getVehicles(null, "2018-12-28 09:23:00");
        PlatformResult result = restTemplate.getForObject("http://tms-mgr/applet/waybill/vehicles?assignTime='018-12-28 09:23:00'", PlatformResult.class);
        return result;
    }

}
