package cn.likegirl.rt.service.feign.fallback;

import cn.likegirl.rt.framework.response.PlatformResult;
import cn.likegirl.rt.service.feign.OrderFeign;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author LikeGirl
 * @version v1.0
 * @title: OrderFeignFallbackFactory
 * @description: TODO
 * @date 2018/12/26 10:25
 */
@Component
public class OrderFeignFallbackFactory implements FallbackFactory<OrderFeign> {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderFeignFallbackFactory.class);

    @Override
    public OrderFeign create(Throwable cause) {
        LOGGER.error("=========>>>> ", cause);
        return new OrderFeign() {
            @Override
            public PlatformResult moduleSiteListByLine(Long customerId, String excludeIds) {
                LOGGER.error("=========>>>> ", cause);
                return PlatformResult.failure("远程调用失败");
            }

            @Override
            public PlatformResult getVehicles(String plateNumber, String assignTime) {
                LOGGER.error("=========>>>> ", cause);
                return PlatformResult.failure("远程调用失败");
            }
        };
    }
}
