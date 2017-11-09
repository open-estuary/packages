package com.htsat.cart.config;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @desc redis config bean
 *
 */
//@Configuration
//@EnableAutoConfiguration
//@ConfigurationProperties(prefix = "spring.redis", locations = "classpath:application.properties")
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private  String host;

	private  int port;

//	private  String password;

	private  int timeout;

	private Map<String, Integer> pool = new HashMap<>();

	private  JedisPool jedisPool = null;

	private  JedisPool initialPool(){
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(pool.get("maxIdle"));
		config.setMaxTotal(pool.get("maxActive"));
		config.setMaxWaitMillis(pool.get("maxWait"));
		try {
			jedisPool = new JedisPool(config,host.split(",")[0],port,timeout);
		} catch (Exception e1) {
			logger.error("first create JedisPool error : " + e1);
			try {
				jedisPool = new JedisPool(config,host.split(",")[1],port,timeout);
			} catch (Exception e2){
				logger.error("second create JedisPool error : " + e2);
				e2.printStackTrace();
			}
		}
		return jedisPool;
	}

	/**
	 * 多线程环境同步初始化
	 */
	private  synchronized void poolInit() {
		if (jedisPool == null) {
			initialPool();
		}
	}

	public synchronized  Jedis getJedis(){
		if (jedisPool == null) {
			poolInit();
		}
		Jedis jedis = null;
		try {
			if (jedisPool != null) {
				jedis = jedisPool.getResource();
			}
		} catch (Exception e) {
			logger.error("Get jedis error : "+e);
			e.printStackTrace();
		}
		return jedis;
	}

	@SuppressWarnings("deprecation")
	public  void returnResource(final Jedis jedis) {
		if (jedis != null && jedisPool !=null) {
			jedisPool.returnResource(jedis);
		}
	}

	public  void set(String key, String value) {
		Jedis jedis = getJedis();
		try{
			value = StringUtils.isEmpty(value) ? "" : value;
			jedis.set(key, value);
			logger.info("Redis set success - " + key + ", value:" + value);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Redis set error: "+ e.getMessage() +" - " + key + ", value:" + value);
		}finally{
			returnResource(jedis);
		}
	}

	public  void set(String key ,int seconds,String value){
		Jedis jedis = getJedis();
		try {
			value = StringUtils.isEmpty(value) ? "" : value;
			jedis.setex(key, seconds, value);
			logger.info("Redis set success - " + key + ", value:" + value);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Redis set error: "+ e.getMessage() +" - " + key + ", value:" + value);
		}finally{
			returnResource(jedis);
		}
	}

	public  String get(String key) {
		if(getJedis() == null || !getJedis().exists(key)){
			return null;
		}
		String result = null;
		Jedis jedis = getJedis();
		try{
			result = jedis.get(key);
			logger.info("Redis get success - " + key + ", value:" + result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Redis set error: "+ e.getMessage() +" - " + key + ", value:" + result);
		}finally{
			returnResource(jedis);
		}
		return result;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Map<String, Integer> getPool() {
		return pool;
	}

	public void setPool(Map<String, Integer> pool) {
		this.pool = pool;
	}
}
