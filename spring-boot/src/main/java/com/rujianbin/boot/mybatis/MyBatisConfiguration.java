package com.rujianbin.boot.mybatis;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ConditionalOnClass({ EnableTransactionManagement.class})    //某某class存在时才会初始化该config
@AutoConfigureAfter({ DataBaseConfiguration.class })
@MapperScan(basePackages={"com.rujianbin.boot.mapper"},annotationClass=Repository.class)
public class MyBatisConfiguration implements EnvironmentAware{

	private RelaxedPropertyResolver propertyResolver;
	
	@Resource(name="dataSource")  
    private DataSource dataSource; 
	
	@Override
	public void setEnvironment(Environment env) {
		this.propertyResolver = new RelaxedPropertyResolver(env,"mybatis.");
	}
	
	@Bean("sqlSessionFactory")
    @ConditionalOnMissingBean  
    public SqlSessionFactory sqlSessionFactory() {  
        try {  
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();  
            sessionFactory.setDataSource(dataSource);  
            sessionFactory.setTypeAliasesPackage(propertyResolver  
                    .getProperty("typeAliasesPackage"));  
            sessionFactory  
                    .setMapperLocations(new PathMatchingResourcePatternResolver()  
                            .getResources(propertyResolver  
                                    .getProperty("mapperLocations")));  
            sessionFactory  
                    .setConfigLocation(new DefaultResourceLoader()  
                            .getResource(propertyResolver  
                                    .getProperty("configLocation")));  
  
            return sessionFactory.getObject();  
        } catch (Exception e) {  
            return null;  
        }  
    }

	
	@Bean("dataSourceTransactionManager")
    @ConditionalOnMissingBean  
    public DataSourceTransactionManager transactionManager() {  
        return new DataSourceTransactionManager(dataSource);  
    } 

}
