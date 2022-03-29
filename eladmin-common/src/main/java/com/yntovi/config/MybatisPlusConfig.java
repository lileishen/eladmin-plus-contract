package com.yntovi.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.yntovi.config.MybatisPlusFillHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件
 * Created by jinjin on 2020-09-21.
 */
@Configuration
@MapperScan(basePackages = {"com.yntovi.**.mapper"})
public class MybatisPlusConfig {


    @Bean
      public MybatisPlusInterceptor mybatisPlusInterceptor() {
          MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
          interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());   //    注册乐观锁插件
          interceptor.addInnerInterceptor(new PaginationInnerInterceptor());    // 分页插件
          return interceptor;
      }

    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MybatisPlusFillHandler());
        return globalConfig;
    }
    
}
