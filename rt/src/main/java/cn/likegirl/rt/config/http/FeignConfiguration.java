package cn.likegirl.rt.config.http;

import cn.likegirl.rt.constant.HeaderConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LikeGirl
 * @version v1.0
 * @title: FeignConfiguration
 * @description: TODO
 * @date 2018/12/26 9:44
 */
//@Configuration
public class FeignConfiguration implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header("X-UserID", "258");
    }

    /*@Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }*/
}
