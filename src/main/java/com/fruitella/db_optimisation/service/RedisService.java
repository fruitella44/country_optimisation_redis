package com.fruitella.db_optimisation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fruitella.db_optimisation.connection.DbConnection;
import com.fruitella.db_optimisation.connection.RedisConnection;
import com.fruitella.db_optimisation.dbEntity.City;
import com.fruitella.db_optimisation.dbEntity.Country;
import com.fruitella.db_optimisation.dbEntity.CountryLanguage;
import com.fruitella.db_optimisation.redisEntity.CityCountry;
import com.fruitella.db_optimisation.redisEntity.Language;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class RedisService {
    private static final Logger LOGGER = LogManager.getLogger(RedisService.class);
    private final RedisClient redisClient;
    private final ObjectMapper mapper;

    public RedisService() {
        redisClient = preparedRedisClient();
        mapper = new ObjectMapper();
        LOGGER.debug("Initialized redisConnection, mapper");
    }

    private RedisClient preparedRedisClient() {
        RedisClient redisClient = RedisConnection.getRedisClient();
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            LOGGER.debug("Current connection to Redis");
            return redisClient;
        }
    }

    public List<CityCountry> transformData(List<City> cities) {
        return cities.stream().map(city -> {
            CityCountry res = new CityCountry();
            res.setId(city.getId());
            res.setName(city.getName());
            res.setPopulation(city.getPopulation());
            res.setDistrict(city.getDistrict());
            LOGGER.debug("Mapping CityCountry");

            Country country = city.getCountry();
            res.setAlternativeCountryCode(country.getAlternativeCode());
            res.setContinent(country.getContinent());
            res.setCountryCode(country.getMainCode());
            res.setCountryName(country.getName());
            res.setCountryPopulation(country.getPopulation());
            res.setCountryRegion(country.getRegion());
            res.setCountrySurfaceArea(country.getSurfaceArea());
            LOGGER.debug("Mapping Country");

            Set<CountryLanguage> countryLanguages = country.getLanguages();
            Set<Language> languages = countryLanguages.stream().map(cl -> {
                Language language = new Language();
                language.setLanguage(cl.getLanguage());
                language.setIsOfficial(cl.getIsOfficial());
                language.setPercentage(cl.getPercentage());
                return language;
            }).collect(Collectors.toSet());
            res.setLanguages(languages);
            LOGGER.debug("Mapping Language");

            return res;
        }).collect(Collectors.toList());
    }

    public void pushToRedis(List<CityCountry> data) {
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            LOGGER.debug("Call redisClient connection");

            for (CityCountry cityCountry : data) {
                try {
                    sync.set(String.valueOf(cityCountry.getId()), mapper.writeValueAsString(cityCountry));
                    LOGGER.debug("Push data to redis");
                } catch (JsonProcessingException exception) {
                    LOGGER.error("Fail parsing with json: " + exception);
                }
            }

        }
    }

    public void testRedisData(List<Integer> ids) {
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            LOGGER.debug("Call redisClient connection");

            for (Integer id : ids) {
                String value = sync.get(String.valueOf(id));
                try {
                    mapper.readValue(value, CityCountry.class);
                    LOGGER.debug("Read data from redis");
                } catch (JsonProcessingException exception) {
                    LOGGER.error("Fail reading redisDb: " + exception);
                }
            }
        }
    }

    public void shutdown() {
        if (nonNull(DbConnection.getSessionFactory())) {
            DbConnection.getSessionFactory().close();
            LOGGER.debug("DbConnection is closed");
        }
        if (nonNull(redisClient)) {
            redisClient.shutdown();
            LOGGER.debug("RedisConnection is closed");
        }
    }
}
