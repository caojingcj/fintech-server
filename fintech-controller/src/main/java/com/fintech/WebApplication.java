package com.fintech;

import java.io.File;

import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.fintech.common.properties.AppConfig;
import com.fintech.service.RedisService;
import com.fintech.util.DateUtils;

/**   
* @Title: Application.java 
* @Package com.fintech 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月12日 上午1:53:22  
* @Description: TODO[ boot入口启动类 ]
*/
@Configuration
@ComponentScan("com.fintech")
//@MapperScan("com.fintech.dao.mapper")
//@RequestMapping(value = {"/",""})
@EnableAutoConfiguration
@PropertySource(value = {"classpath:jdbc.properties","classpath:serviceconfig.properties"}, ignoreResourceNotFound = true)
public class WebApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer{
    private static Logger logger = LoggerFactory.getLogger(WebApplication.class);
    
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private RedisService redisService;
    
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class ,args);
    }
    
    /**
     * 文件上传临时路径
     * 支持Linux路径（网络图片流需要临时文件夹地址）
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
   	 MultipartConfigFactory factory = new MultipartConfigFactory();
       try {
       	File file = new File(appConfig.getTmpFile());
			redisService.set("tmpFile", file.getPath());
			logger.info("EK 创建临时文件夹路径[{}]方法名[{}]操作时间[{}]",file,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
			if (!file.exists()) {
				file.mkdirs();
			}
			factory.setLocation(file.getPath());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EK 创建临时文件夹失败：可能无权限访问！方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
		}
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
            return application.sources(WebApplication.class);
        }
     
     /**
      * 自定义端口
      */
     @Override
     public void customize(ConfigurableEmbeddedServletContainer container){
         container.setPort(appConfig.getServer_port());
     }
}
