package com.htsat.search.serviceimpl;

import com.htsat.search.config.RedisConfig;
import com.htsat.search.service.ISearchService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class SearchServiceImpl implements ISearchService {

    private Logger logger = Logger.getLogger(com.htsat.search.serviceimpl.SearchServiceImpl.class);

    @Autowired
    private RedisConfig redisConfig;

    @Override
    public String getskuByRedis(String skuId) {

        Jedis jedis = redisConfig.getJedis();
        if (jedis == null || !jedis.exists(skuId + "")) {
            return null;
        }
        String skuJson = null;
        try {
            skuJson = jedis.get(skuId);
        } catch (Exception e) {
            logger.error("Redis get error: " + e.getMessage() + " - key : " + skuId);
        } finally {
            redisConfig.returnResource(jedis);
        }

        if (StringUtils.isEmpty(skuJson))
            return "";
        else
            return skuJson;
    }

}
