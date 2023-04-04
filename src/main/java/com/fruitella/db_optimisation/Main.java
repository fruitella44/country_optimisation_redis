package com.fruitella.db_optimisation;

import com.fruitella.db_optimisation.connection.DbConnection;
import com.fruitella.db_optimisation.entity.City;
import com.fruitella.db_optimisation.redis.CityCountry;
import com.fruitella.db_optimisation.service.CityService;
import com.fruitella.db_optimisation.service.CountryService;
import com.fruitella.db_optimisation.service.RedisService;

import java.util.List;

public class Main {
    private final CityService cityService;
    private final CountryService countryService;
    private RedisService redisService;
    private final int STEP = 500;
    private final List<Integer> RANDOM_CITES = List.of(3, 2545, 123, 4, 189, 89, 3458, 1189, 10, 102);

    public Main() {
        cityService = new CityService();
        countryService = new CountryService();
        redisService = new RedisService();

    }


    public static void main(String[] args) {
        Main main = new Main();
        List<City> allCities = main.cityService.fetchData(main.STEP);
        List<CityCountry> preparedData = main.redisService.transformData(allCities);
        main.redisService.pushToRedis(preparedData);
        DbConnection.getSessionFactory().getCurrentSession().close();

        long startRedis = System.currentTimeMillis();
        main.redisService.testRedisData(main.RANDOM_CITES);
        long stopRedis = System.currentTimeMillis();

        long startMysql = System.currentTimeMillis();
        main.cityService.testMysqlData(main.RANDOM_CITES);
        long stopMysql = System.currentTimeMillis();

        System.out.printf("%s:\t%d ms\n", "Redis", (stopRedis - startRedis));
        System.out.printf("%s:\t%d ms\n", "MySQL", (stopMysql - startMysql));

        main.redisService.shutdown();

    }
}
