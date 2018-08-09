package cn.likegirl.rt.config.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@EnableTransactionManagement
@AutoConfigureAfter({ MybatisConfiguration.class })
@Configuration
public class TransactionConfiguration implements TransactionManagementConfigurer {

    @Autowired
    private ReadWriteSplitRoutingDataSource dataSource;

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 事物随线程当前数据源
     */
}

