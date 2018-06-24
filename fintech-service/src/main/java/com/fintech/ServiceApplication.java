package com.fintech;

import java.io.File;

import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.fintech.util.DateUtils;

@Configuration
@ComponentScan("com.fintech")
@EnableAutoConfiguration
@PropertySource(value = {"classpath:jdbc.properties","classpath:serviceconfig.properties"}, ignoreResourceNotFound = true)
public class ServiceApplication extends SpringBootServletInitializer{
    private static Logger logger = LoggerFactory.getLogger(ServiceApplication.class);
    
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class ,args);
    }
    
    /**
     * 文件上传临时路径
     */
     @Bean
     MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = System.getProperty("user.dir") + "/data/tmp";
        logger.info("EK 创建临时文件夹路径[{}]方法名[{}]操作时间[{}]",location,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }
    @Bean
    public ErrorPageFilter errorPageFilter() {
        return new ErrorPageFilter();
    }

    @Bean
    public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }
    
     @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
            return application.sources(ServiceApplication.class);
        }
}
