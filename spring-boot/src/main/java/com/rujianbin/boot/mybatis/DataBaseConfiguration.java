package com.rujianbin.boot.mybatis;

import javax.sql.DataSource;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration  
@EnableTransactionManagement
public class DataBaseConfiguration implements EnvironmentAware{

	private RelaxedPropertyResolver propertyResolver;
	
	@Override
	public void setEnvironment(Environment env) {
		this.propertyResolver = new RelaxedPropertyResolver(env, "jdbc.");
	}

	@Bean(name="dataSource")  
    public DataSource getDataSource() {  
        DruidDataSource datasource = new DruidDataSource();  
        datasource.setUrl(propertyResolver.getProperty("url"));  
        datasource.setDriverClassName(propertyResolver.getProperty("driverClass"));  
        datasource.setUsername(propertyResolver.getProperty("username"));  
        datasource.setPassword(propertyResolver.getProperty("password"));  
          
        return datasource;  
    }
	
}
