package com.rujianbin.redis.jedisCluster;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClusterFactory implements FactoryBean<JedisCluster>, InitializingBean{

	private static final Logger  logger = LoggerFactory.getLogger(JedisClusterFactory.class);
	
	private static final int DEFAULT_TIMEOUT = 5000;
	private static final int DEFAULT_MAX_REDIRECTIONS = 5;
	
	private JedisPoolConfig jedisPoolConfig;
	private JedisCluster jedisCluster;

	private Integer timeout = DEFAULT_TIMEOUT;  
    private Integer maxRedirections = DEFAULT_MAX_REDIRECTIONS;
    private String redisHostAndPort;

    private Pattern p = Pattern.compile("^\\d{1,3}(\\.\\d{1,3}){3}[:]\\d{1,5}\\s*$");
    private String hostSplit = "[,]|[ ]|[;]";
	@Override
	public void afterPropertiesSet() throws Exception {
		Set<HostAndPort> haps = this.parseHostAndPort();
		jedisCluster = new JedisCluster(haps, timeout, maxRedirections, jedisPoolConfig); 
	}

	@Override
	public JedisCluster getObject() throws Exception {
		return jedisCluster;
	}

	@Override
	public Class<?> getObjectType() {
		return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	private Set<HostAndPort> parseHostAndPort() throws Exception {  
        try {  
            if(StringUtils.isBlank(redisHostAndPort)) {
            	throw new IllegalArgumentException("REDIS HOST AND PORT IS NULL !!");
            }
            String[] hostAndPorts = redisHostAndPort.trim().split(hostSplit);
            Set<HostAndPort> haps = new HashSet<HostAndPort>();  
            for (String hostAndPort : hostAndPorts) {  
   
  
                boolean isIpPort = p.matcher(hostAndPort).matches();  
  
                if (!isIpPort) {  
                    throw new IllegalArgumentException("REDIS HOST AND PORT Illegal");  
                }  
                String[] ipAndPort = hostAndPort.split(":");  
  
                HostAndPort hap = new HostAndPort(ipAndPort[0].trim(), Integer.parseInt(ipAndPort[1].trim()));  
                haps.add(hap);  
            }  
  
            return haps;  
        } catch (IllegalArgumentException ex) {  
        	logger.error("redis配置不合法",ex);
            throw ex;  
        } catch (Exception e) {  
            throw new Exception("redis加载失败", e);  
        }  
    }

	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}


	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getMaxRedirections() {
		return maxRedirections;
	}

	public void setMaxRedirections(Integer maxRedirections) {
		this.maxRedirections = maxRedirections;
	}

	public JedisPoolConfig getJedisPoolConfig() {
		return jedisPoolConfig;
	}

	public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
		this.jedisPoolConfig = jedisPoolConfig;
	}

	public String getRedisHostAndPort() {
		return redisHostAndPort;
	}

	public void setRedisHostAndPort(String redisHostAndPort) {
		this.redisHostAndPort = redisHostAndPort;
	}


	
	
}
