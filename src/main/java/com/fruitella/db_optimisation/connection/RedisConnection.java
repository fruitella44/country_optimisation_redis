package com.fruitella.db_optimisation.connection;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisConnectionException;
import io.lettuce.core.RedisURI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RedisConnection {
    private static final Logger LOGGER = LogManager.getLogger(RedisConnection.class);
    private static final String REDIS_URL = "localhost";
    private static final int REDIS_PORT = 6379;
    private static final RedisClient redisClient = getConnection();

    private static RedisClient getConnection() throws RedisConnectionException {
        LOGGER.debug("Connect to Redis with: " + REDIS_URL + " " + REDIS_PORT);
        return RedisClient.create(RedisURI.create(REDIS_URL, REDIS_PORT));
    }

    public static RedisClient getRedisClient() {
        return redisClient;
    }
}


