package com.fruitella.db_optimisation;

import com.fruitella.db_optimisation.connection.DbConnection;
import com.fruitella.db_optimisation.dbEntity.City;
import com.fruitella.db_optimisation.redisEntity.CityCountry;
import com.fruitella.db_optimisation.service.CityService;
import com.fruitella.db_optimisation.service.CountryService;
import com.fruitella.db_optimisation.service.RedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final CityService cityService;
    private static final CountryService countryService;
    private static final RedisService redisService;
    private static final int STEP = 500;
    private static final List<Integer> RANDOM_CITES = List.of(3, 2545, 123, 4, 189, 89, 3458, 1189, 10, 102);

    static {
        cityService = new CityService();
        countryService = new CountryService();
        redisService = new RedisService();
        LOGGER.debug("Initialized services: [cityService, countryService, redisService]");
    }

    public static void main(String[] args) {
        List<City> allCities = cityService.fetchData(STEP);
        LOGGER.debug("Load data from db with step: [" + STEP + "]");

        List<CityCountry> preparedData = redisService.transformData(allCities);
        LOGGER.debug("Parsing with json format");
        redisService.pushToRedis(preparedData);
        DbConnection.getSessionFactory().getCurrentSession().close();
        LOGGER.debug("Push data to Redis. Close currentDataBaseConnection.");

        long startRedis = System.currentTimeMillis();
        redisService.testRedisData(RANDOM_CITES);
        long stopRedis = System.currentTimeMillis();
        LOGGER.debug("Scaling ms redisResponse");

        long startMysql = System.currentTimeMillis();
        cityService.testMysqlData(RANDOM_CITES);
        long stopMysql = System.currentTimeMillis();
        LOGGER.debug("Scaling ms mySqlResponse");

        System.out.printf("%s:\t%d ms\n", "Redis", (stopRedis - startRedis));
        System.out.printf("%s:\t%d ms\n", "MySQL", (stopMysql - startMysql));

        redisService.shutdown();
        LOGGER.debug("Close allConnections");
    }
}
