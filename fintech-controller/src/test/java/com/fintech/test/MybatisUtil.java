package com.fintech.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory; 

/**   
* @Title: MybatisUtil.java 
* @Package com.mybatis.util 
* @author qierkang xyqierkang@163.com   
* @date 2017年11月28日 下午8:36:37  
* @Description: TODO[ Mybatis工具类 ]
*/
public class MybatisUtil {
    private static SqlSessionFactory sqlSessionFactory;

    /** 
    * @Title: MybatisUtil.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2017年11月28日 下午8:29:55  
    * @param @param cls
    * @param @return
    * @param @throws IOException    设定文件 
    * @Description: TODO[ 这里用一句话描述这个方法的作用 ]
    * @throws \
    */
    public static SqlSessionFactory getNewSessionFactory(Class<?> cls,boolean flag) throws IOException {
        if (sqlSessionFactory == null) {
            PooledDataSource dataSource = new PooledDataSource();
            dataSource.setDriver("com.mysql.jdbc.Driver");
            //flag=true?正式环境:测试环境
            if(flag){
                dataSource.setUrl("jdbc:mysql://192.168.5.230:3306/yfq_data_center?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");
                dataSource.setUsername("asset");
                dataSource.setPassword("mr8ZRfs9GsfnwQ8pFfub");
            }else{
                dataSource.setUrl("jdbc:mysql://192.168.5.222:3306/yfq_data_center?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");
                dataSource.setUsername("mysql-test");
                dataSource.setPassword("8XPqDrhAHCkE6n7TDlNd");
            }
            TransactionFactory transactionFactory = new JdbcTransactionFactory();
            Environment environment = new Environment("development", transactionFactory,
                dataSource);
            Configuration configuration = new Configuration(environment);
            configuration.addMapper(cls);
            return new SqlSessionFactoryBuilder().build(configuration);
        } else {
            return sqlSessionFactory;
        }
    }
    
    
    /** 
    * @Title: MybatisUtil.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2017年11月28日 下午8:29:49  
    * @param @param dataBase
    * @param @param cls
    * @param @return    设定文件 
    * @Description: TODO[ 这里用一句话描述这个方法的作用 ]
    * @throws 
    */
    public static SqlSession getSqlSession(String dataBase,boolean flag,Class<?> cls){
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
    
    /** 
    * @Title: MybatisUtil.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2017年11月28日 下午8:36:51  
    * @param @param args    设定文件 
    * @Description: TODO[ 
    * 1.NEWDATASOURCE》获取哪个数据库
    * 2.false true 连接测试还是正式 false测试 true正式
    * 3.ManageCompanyMapper.class 实现接口
    *  ]
    * @throws 
    */
    public static void main(String[] args) {
        SqlSession session=MybatisUtil.getSqlSession("newDataSource",true, ManageCompanyMapper.class);
        List<ManageCompany> manageCompanies=session.getMapper(ManageCompanyMapper.class).selectByPrimaryKeys();
        for (ManageCompany manageCompany : manageCompanies) {
            System.out.println(manageCompany);
        }
    }
}
