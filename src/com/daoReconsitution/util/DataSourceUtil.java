package com.daoReconsitution.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceUtil {


    //获取dbcp方式的ds对象
    public static DataSource getDataSourceWIthDBCP(){
        BasicDataSource dbcp = new BasicDataSource();
        dbcp.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dbcp.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:ORCL");
        dbcp.setUsername("scott");
        dbcp.setPassword("123456");
        dbcp.setInitialSize(20);
        dbcp.setMaxIdle(10);
        return dbcp;
    }


    public static DataSource getDataSourceWIthDBCPByProperties() throws Exception{
        DataSource dbcp = null ;
        Properties props = new Properties();
        InputStream input = new DataSourceUtil().getClass().getClassLoader().getResourceAsStream("dbcpconfig.properties");
        props.load(  input );

        //只需要记住以下一句
        dbcp = BasicDataSourceFactory.createDataSource(props ) ;
        return dbcp;
    }

    public static DataSource getDataSourceWithC3P0(){
        ComboPooledDataSource c3p0 = new ComboPooledDataSource();
        try {
            c3p0.setDriverClass("oracle.jdbc.driver.OracleDriver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        c3p0.setJdbcUrl("jdbc:oracle:thin:@127.0.0.1:1521:ORCL");
        c3p0.setUser("scott");
        c3p0.setPassword("123456");

        return c3p0 ;
    }

    public static DataSource getDataSourceWithC3P0ByXml(){
        ComboPooledDataSource c3p0 = new ComboPooledDataSource("yanqun");
        return c3p0 ;
    }
}
