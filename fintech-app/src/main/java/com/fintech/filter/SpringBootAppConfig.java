package com.fintech.filter;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**   
* @Title: WebConfig.java 
* @Package com.medcfc.controller.webconfig 
* @author qierkang xyqierkang@163.com   
* @date 2018年5月5日 下午3:23:37  
* @Description: TODO[ 初始化拦截器 ]
*/
@Configuration
public class SpringBootAppConfig extends WebMvcConfigurerAdapter {
	/* (非 Javadoc) 
	* <p>Title: addInterceptors</p> 
	* <p>Description: </p> 
	* @param registry 
	* @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry) 
	*初始化拦截器	
	*/
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogHandlerInterceptor());
	}
	
	@Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

}
