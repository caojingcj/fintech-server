package com.fintech.dao.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = FinTechDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "fintechSqlSessionFactory")
public class FinTechDataSourceConfig {

    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.fintech.dao";
    static final String MAPPER_LOCATION = "classpath*:mapper/fintech/*.xml";

    //    @Value("${fintech.publicKey}")
//    private String publicKey;

    @Bean(name = "fintechDataSource")
    @Primary
    public DataSource csDataSource() {
    	DataBean dataBean=new DataBean();
        return DruidDataSources.getDataSource(dataBean.getDriver(),dataBean.getUrl(),dataBean.getUsername(),dataBean.getPassword());
    }

    @Bean(name = "fintechTransactionManager")
    @Primary
    public DataSourceTransactionManager csTransactionManager() {
        return new DataSourceTransactionManager(csDataSource());
    }

    @Bean(name = "fintechSqlSessionFactory")
    @Primary
    public SqlSessionFactory csSqlSessionFactory(@Qualifier("fintechDataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(FinTechDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }


    /**
     * 有多少个从库就要配置多少个 cslication.properties 方式
     * @return
     */
//    @Bean(name = "readDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.read")
//    public DataSource readDataSourceOne() {
//        return DataSourceBuilder.create().type(dataSourceType).build();
//    }

}
