package com.fruitella.db_optimisation.connection;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisConnectionException;
import io.lettuce.core.RedisURI;

public class RedisConnection {
    private static final String REDIS_URL = "localhost";
    private static final int REDIS_PORT = 6379;
    private static final RedisClient redisClient = getConnection();

    private static RedisClient getConnection() throws RedisConnectionException {
        return RedisClient.create(RedisURI.create(REDIS_URL, REDIS_PORT));
    }

    public static RedisClient getRedisClient() {
        return redisClient;
    }
}


