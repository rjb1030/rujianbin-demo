package com.rujianbin.freemark.autoconfiguration;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration.FreeMarkerWebConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.rujianbin.freemark.autoconfiguration.WebFreeMarkerConfigurer.FreeMarkerVariables;
/**
 * 定义一些freemarker的全局变量
 * @author rujianbin
 *
 */

@Configuration
@ConditionalOnClass({Servlet.class})
@ConditionalOnWebApplication
@EnableConfigurationProperties(FreeMarkerVariables.class)
public class WebFreeMarkerConfigurer extends FreeMarkerWebConfiguration{

	  @Bean
	  @ConditionalOnMissingBean({FreeMarkerConfig.class})
	  public FreeMarkerConfigurer freeMarkerConfigurer(FreeMarkerVariables freeMarkerVariables) {
	    FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
	    configurer.setFreemarkerVariables(freeMarkerVariables.getVariables());
	    applyProperties(configurer);
	    return configurer;
	  }
	
	  @ConfigurationProperties("spring.freemarker")
	  public static class FreeMarkerVariables
	  {
	    Map<String, Object> variables = new HashMap();

	    public Map<String, Object> getVariables() {
	      return this.variables;
	    }

	    public void setVariables(Map<String, Object> variables) {
	      this.variables = variables;
	    }
	  }
}
