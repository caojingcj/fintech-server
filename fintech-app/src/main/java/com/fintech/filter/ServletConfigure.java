package com.fintech.filter;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fintech.controller.weixin.WxCodeServlet;
import com.fintech.controller.weixin.WxOpenIdServlet;

@Configuration
public class ServletConfigure {
    @Bean
    public ServletRegistrationBean wxServlet() {
        return new ServletRegistrationBean(new WxCodeServlet(), "/wxServlet");
    }
    
    @Bean
    public ServletRegistrationBean wxOpenIdServlet() {
        return new ServletRegistrationBean(new WxOpenIdServlet(), "/wxOpenIdServlet");
    }
}
