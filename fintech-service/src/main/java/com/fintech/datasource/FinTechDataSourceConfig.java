package com.fintech.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fintech.datasource.druid.DruidDataSources;
import com.fintech.util.enumerator.ConstantInterface;


@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.fintech.dao", sqlSessionFactoryRef = "fintechSqlSessionFactory")
public class FinTechDataSourceConfig {
    @Autowired
    private DataBean dataBean;

    @Bean(name = "fintechDataSource")
    @Primary
    public DataSource csDataSource() {
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
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(ConstantInterface.DruidDataConfig.DRUIDDATA_CONFIG.FINTECH_MAPPER.getValue()));
        return sessionFactory.getObject();
    }

}
