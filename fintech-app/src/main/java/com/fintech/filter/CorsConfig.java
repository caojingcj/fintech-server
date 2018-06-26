package com.fintech.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**   
* @Title: CorsConfig.java 
* @Package com.medcfc.controller.config 
* @author qierkang xyqierkang@163.com   
* @date 2018年3月23日 下午1:10:05  
* @Description: TODO[ spring boot 解决post get所有跨域问题 ]
允许任何域名使用
允许任何头 
允许任何方法（post、get等）
*/
@Configuration
public class CorsConfig {
	private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1
        corsConfiguration.addAllowedHeader("*"); // 2
        corsConfiguration.addAllowedMethod("*"); // 3
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }
}
