package cn.likegirl.rt.service.feign;

import cn.likegirl.rt.framework.response.PlatformResult;
import cn.likegirl.rt.service.feign.fallback.OrderFeignFallback;
import cn.likegirl.rt.service.feign.fallback.OrderFeignFallbackFactory;
import cn.likegirl.rt.utils.BusinessMap;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author LikeGirl
 * @version v1.0
 * @title: OrderFeign
 * @description: TODO
 * @date 2018/12/18 12:10
 */
//@FeignClient(name="tms-mgr", fallback = OrderFeignFallback.class)
@FeignClient(name="tms-mgr", fallbackFactory = OrderFeignFallbackFactory.class)
public interface OrderFeign {


    @RequestMapping(value = "/applet/site/list/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public PlatformResult moduleSiteListByLine(@PathVariable("customerId") Long customerId,@RequestParam(name = "excludeIds", required = false) String excludeIds);

    @RequestMapping(value = "/applet/waybill/vehicles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PlatformResult getVehicles(@RequestParam(value = "plateNumber", required = false) String plateNumber, @RequestParam(name = "assignTime") String assignTime);

}
