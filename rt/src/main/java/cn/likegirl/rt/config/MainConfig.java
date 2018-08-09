/**
 * Copyright 2017 JINZAY All Rights Reserved.
 */
package cn.likegirl.rt.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author bhz
 * @since 2017年2月8日 下午1:28:48
 */
//@EnableWebMvc		//启用了spring mvc
@Configuration		// 让spring boot 项目启动时识别当前配置类
@ComponentScan({"cn.likegirl.rt.*"})
@EnableTransactionManagement
public class MainConfig {

}
