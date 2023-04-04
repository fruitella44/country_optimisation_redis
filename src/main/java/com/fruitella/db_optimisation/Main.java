package com.fruitella.db_optimisation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fruitella.db_optimisation.domain.CityDaoIml;
import com.fruitella.db_optimisation.domain.CountryDaoIml;
import com.fruitella.db_optimisation.connection.DbConnectionSessionFactory;
import com.fruitella.db_optimisation.entity.City;
import io.lettuce.core.RedisClient;
import org.hibernate.SessionFactory;

import java.util.List;

import static java.util.Objects.nonNull;

public class Main {
    private final SessionFactory sessionFactory;
    private RedisClient redisClient;
    private final ObjectMapper mapper;
    private final CityDaoIml cityDao;
    private final CountryDaoIml countryDAO;
    private final int STEP = 500;

    public Main() {
        sessionFactory = DbConnectionSessionFactory.getSessionFactory();
//        redisClient = preparedRedicClient();
        mapper = new ObjectMapper();
        cityDao = new CityDaoIml(sessionFactory);
        countryDAO = new CountryDaoIml(sessionFactory);

    }


    public static void main(String[] args) {
        Main main = new Main();
        List<City> allCities = main.cityDao.fetchData(main.STEP);
        for (City city: allCities) {
            System.out.println(city.getName());
        }
        main.shutdown();
    }

    private void shutdown() {
        if (nonNull(DbConnectionSessionFactory.getSessionFactory())) {
            DbConnectionSessionFactory.getSessionFactory().close();
        }
        if (nonNull(redisClient)) {
            redisClient.shutdown();
        }
    }

}
