package cn.likegirl.rt.service.feign;

import cn.likegirl.rt.framework.response.PlatformResult;
import cn.likegirl.rt.service.feign.fallback.OrderFeignFallback;
import cn.likegirl.rt.utils.BusinessMap;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author LikeGirl
 * @version v1.0
 * @title: OrderFeign
 * @description: TODO
 * @date 2018/12/18 12:10
 */
@FeignClient(name="tms-mgr", fallback = OrderFeignFallback.class)
public interface OrderFeign {


    @RequestMapping(value = "/applet/site/list/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public PlatformResult moduleSiteListByLine(@PathVariable("customerId") Long customerId,@RequestParam(name = "excludeIds", required = false) String excludeIds);

}
