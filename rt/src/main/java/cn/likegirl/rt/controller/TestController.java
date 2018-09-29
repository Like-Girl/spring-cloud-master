package cn.likegirl.rt.controller;

import cn.likegirl.rt.framework.exception.ParameterInvalidException;
import cn.likegirl.rt.framework.response.PlatformResult;
import cn.likegirl.rt.utils.BusinessMap;
import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/user")
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "error", commandProperties = {
            @HystrixProperty(name="execution.isolation.strategy", value = "THREAD"),
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
    public PlatformResult hello(@PathVariable Long id){
        if(id > 50){
            throw new ParameterInvalidException();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        MultiValueMap<String, Object> params= new LinkedMultiValueMap<>();
        params.add("userId",242);
        params.add("projectId",352);
        params.add("assignTime","2018-09-27 10:24:12");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
//        try {
//            Thread.sleep(10 * 1000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        ResponseEntity<PlatformResult> response = restTemplate.exchange("http://tms-mgr/applet/order/fixed-vehicle", HttpMethod.POST,requestEntity,PlatformResult.class);
        PlatformResult result = restTemplate.postForObject("http://tms-mgr/applet/order/fixed-vehicle",requestEntity,PlatformResult.class);
        return result;
    }

    public PlatformResult error(Long id) {
        return PlatformResult.failure(id+",我被降级了");
    }

}
