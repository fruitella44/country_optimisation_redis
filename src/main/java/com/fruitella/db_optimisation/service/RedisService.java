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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class RedisService {
    private final RedisClient redisClient;
    private final ObjectMapper mapper;

    public RedisService() {
        redisClient = preparedRedisClient();
        mapper = new ObjectMapper();
    }

    private RedisClient preparedRedisClient() {
        RedisClient redisClient = RedisConnection.getRedisClient();
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            System.out.println("\nConnected to Redis\n");
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

            Country country = city.getCountry();
            res.setAlternativeCountryCode(country.getAlternativeCode());
            res.setContinent(country.getContinent());
            res.setCountryCode(country.getMainCode());
            res.setCountryName(country.getName());
            res.setCountryPopulation(country.getPopulation());
            res.setCountryRegion(country.getRegion());
            res.setCountrySurfaceArea(country.getSurfaceArea());

            Set<CountryLanguage> countryLanguages = country.getLanguages();
            Set<Language> languages = countryLanguages.stream().map(cl -> {
                Language language = new Language();
                language.setLanguage(cl.getLanguage());
                language.setIsOfficial(cl.getIsOfficial());
                language.setPercentage(cl.getPercentage());
                return language;
            }).collect(Collectors.toSet());
            res.setLanguages(languages);

            return res;
        }).collect(Collectors.toList());
    }

    public void pushToRedis(List<CityCountry> data) {
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            for (CityCountry cityCountry : data) {
                try {
                    sync.set(String.valueOf(cityCountry.getId()), mapper.writeValueAsString(cityCountry));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void testRedisData(List<Integer> ids) {
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            for (Integer id : ids) {
                String value = sync.get(String.valueOf(id));
                try {
                    mapper.readValue(value, CityCountry.class);
                } catch (JsonProcessingException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public void shutdown() {
        if (nonNull(DbConnection.getSessionFactory())) {
            DbConnection.getSessionFactory().close();
        }
        if (nonNull(redisClient)) {
            redisClient.shutdown();
        }
    }
}
