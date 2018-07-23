package com.fintech.datasource;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fintech.util.DateUtils;

/**   
* @Title: DataBean.java 
* @Package com.fintech.common.datasource 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月10日 上午4:55:26  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
@Component
public class DataBean {
    private static final Logger logger = LoggerFactory.getLogger(DataBean.class);
    @Value("${fintech.url}")
    private String url;
    @Value("${fintech.username}")
    private String username;
    @Value("${fintech.password}")
    private String password;
    @Value("${fintech.driver}")
    private String driver;
    @Value("${test.config.properties}")
    private String testConfigProperties;
    @Value("${test.jdbc.properties}")
    private String testJdbcProperties;

    static {
        logger.info("EK 执行静态块static 操作时间[{}]",DateUtils.getDateTime());
    }
    
    /** 
    * @Title: DataBean.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月10日 上午4:55:33  
    * @param     设定文件 
    * @Description: TODO[ 这里用一句话描述这个方法的作用 ]
    * @throws 
    */
    @PostConstruct
    public void init() {
        logger.info("EK 初始化service配置文件[{}][{}] >执行spring初始化PostConstruct操作时间[{}]",testConfigProperties,testJdbcProperties,DateUtils.getDateTime());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "DataBean [url=" + url + ", username=" + username + ", password=" + password + ", driver=" + driver
                + "]";
    }

}
