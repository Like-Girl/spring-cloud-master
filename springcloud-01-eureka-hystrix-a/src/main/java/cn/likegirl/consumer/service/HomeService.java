package cn.likegirl.consumer.service;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HomeService {
	
	@SuppressWarnings("static-access")
	@HystrixCommand(fallbackMethod = "fallbackGetId")
	public String getId() {
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 10; i++) {
			sb.append(Integer.toString((int)(1+Math.random()*10)));
		}
		return sb.toString();
	}
	
	public String fallbackGetId() {
		System.err.println("我被降级了....");
		return "我被降级了....";
	}
}
