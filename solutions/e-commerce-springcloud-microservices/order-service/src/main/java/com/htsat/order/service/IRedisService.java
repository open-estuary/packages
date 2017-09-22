package com.htsat.order.service;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author vic
 * @desc redis service
 */
public interface IRedisService {

	public Jedis getResource();

	public void returnResource(Jedis jedis);

	public void set(String key, String value);

	public String get(String key);

}