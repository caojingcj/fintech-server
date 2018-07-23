package com.fintech.test;

import java.io.IOException;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.fintech.dao.CompanyBaseinfoMapper;
import com.fintech.model.CompanyBaseinfo; 

public class MybatisUtil {
    private static SqlSessionFactory sqlSessionFactory;
    public static SqlSessionFactory getNewSessionFactory(Class<?> cls,boolean flag) throws IOException {
        if (sqlSessionFactory == null) {
            PooledDataSource dataSource = new PooledDataSource();
            dataSource.setDriver("com.mysql.jdbc.Driver");
            //flag=true?正式环境:测试环境
            dataSource.setUrl("jdbc:mysql://rm-uf6pnuf480169p1dpwo.mysql.rds.aliyuncs.com:3306/fintech-test?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");
            dataSource.setUsername("root");
            dataSource.setPassword("19840723cJ");
            TransactionFactory transactionFactory = new JdbcTransactionFactory();
            Environment environment = new Environment("fintech", transactionFactory,
                dataSource);
            Configuration configuration = new Configuration(environment);
            configuration.addMapper(cls);
            return new SqlSessionFactoryBuilder().build(configuration);
        } else {
            return sqlSessionFactory;
        }
    }
    
    
    public static SqlSession getSqlSession(boolean flag,Class<?> cls){
        SqlSession session=null;
        SqlSessionFactory sessionFactory=null;
        try {
            sessionFactory = MybatisUtil.getNewSessionFactory(cls,flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        session = sessionFactory.openSession();
        return session;
    }
    
    public static void main(String[] args) {
        SqlSession session=MybatisUtil.getSqlSession(true, CompanyBaseinfoMapper.class);
        CompanyBaseinfo manageCompanies=session.getMapper(CompanyBaseinfoMapper.class).selectByPrimaryKeyInfo("000002");
        System.out.println(manageCompanies.toString());
    }
}
