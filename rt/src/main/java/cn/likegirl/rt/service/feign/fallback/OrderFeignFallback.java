package cn.likegirl.rt.service.feign.fallback;

import cn.likegirl.rt.framework.response.PlatformResult;
import cn.likegirl.rt.service.feign.OrderFeign;
import org.springframework.stereotype.Component;

/**
 * @author LikeGirl
 * @version v1.0
 * @title: OrderFeignFallback
 * @description: TODO
 * @date 2018/12/18 12:14
 */
@Component
public class OrderFeignFallback implements OrderFeign {

    @Override
    public PlatformResult moduleSiteListByLine(Long customerId, String excludeIds) {
        return PlatformResult.failure("远程调用失败");
    }
}
