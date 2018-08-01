package cn.likegirl.consumer.service.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = {RequestMethod.GET})
	public Map<String,Object> get(){
		Map<String,Object> result = new HashMap<>();
		try {
			result = restTemplate.getForObject("http://provide-service/home/{1}", Map.class, "201804251443");
			
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			result.put("status", 500);
			result.put("msg", "请求失败");
			result.put("data", null);
			System.err.println("error....");
		}
		return result;
	}

}
