package com.fintech.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@ServletComponentScan
@ComponentScan(basePackages={"com.fintech"})
public class Application extends SpringBootServletInitializer{
	  /**
     * 将spring boot配置信息build到容器
     *
     * extends SpringBootServletInitializer (继承用于加载配置到容器中，如兼容tomcat)
     *
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        System.out.println("test1");
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class ,args);
    }
}
