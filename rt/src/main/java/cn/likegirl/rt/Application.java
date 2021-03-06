package cn.likegirl.rt;

import cn.likegirl.rt.config.http.RibbonAgentInterceptor;
import cn.likegirl.rt.framework.interceptor.RestUserAgentInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

//@SpringCloudApplication // @SpringCloudApplication 相当于 @EnableCircuitBreaker @EnableDiscoveryClient @SpringBootApplication
@EnableCircuitBreaker // 开启断路器 或者 @EnableHystrix
@EnableDiscoveryClient	//表示我是一个服务,注册到服务中心上
@EnableFeignClients // 表示开启Feign
@SpringBootApplication
public class Application {
	
	
	@Bean
	@LoadBalanced		//自动负载均衡： 他的机制是（通过）application name 去寻找服务发现 然后去做负载均衡策略的
	public RestTemplate restTemplate(){
		RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory());
		// 两个代码相同
//		restTemplate.setInterceptors(Collections.singletonList(new RestUserAgentInterceptor()));
		restTemplate.setInterceptors(Collections.singletonList(new RibbonAgentInterceptor()));
		return restTemplate;
	}

	@Bean
	@ConfigurationProperties(prefix = "custom.rest")
	public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory(){
//		HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
//		httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(1000);
//		httpComponentsClientHttpRequestFactory.setConnectTimeout(1000);
//		httpComponentsClientHttpRequestFactory.setReadTimeout(3000);
		return new HttpComponentsClientHttpRequestFactory();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
