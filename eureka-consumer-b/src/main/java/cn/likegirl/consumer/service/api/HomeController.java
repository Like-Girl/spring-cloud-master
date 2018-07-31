package cn.likegirl.consumer.service.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {
	
	@RequestMapping(value = "/{sid}",method = {RequestMethod.GET})
	public Map<String,Object> get(@PathVariable("sid") String sid){
		System.err.println("B:" + sid);
		Map<String,Object> result = new HashMap<>();
		result.put("status", 200);
		result.put("msg", "请求成功");
		result.put("data", "consumer b: hi!");
		return result;
	}

}
