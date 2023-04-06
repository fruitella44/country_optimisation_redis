package com.fruitella.db_optimisation.service;

import com.fruitella.db_optimisation.DAO.CityDaoIml;
import com.fruitella.db_optimisation.connection.DbConnection;
import com.fruitella.db_optimisation.dbEntity.City;
import com.fruitella.db_optimisation.dbEntity.CountryLanguage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class CityService {

    private static final Logger LOGGER = LogManager.getLogger(CityService.class);
    private final CityDaoIml cityDao;
    private final SessionFactory sessionFactory;

    public CityService() {
        sessionFactory = DbConnection.getSessionFactory();
        cityDao = new CityDaoIml(sessionFactory);
        LOGGER.debug("Initialized session: [sessionFactory, cityDao]");
    }

    private List<City> getItems(int offset, int limit) {
        return cityDao.getItems(offset, limit);
    }

    private int getTotalCount() {
        return cityDao.getTotalCount();
    }

    private City getById(Integer cityId) {
        return cityDao.getById(cityId);
    }

    public List<City> fetchData(int step) {
        List<City> getAllCities = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            LOGGER.debug("fetchData. Start transaction");

            int totalCount = getTotalCount();
            LOGGER.debug("Call getTotalCount. Added step increment");

            for (int i = 0; i < totalCount; i += step) {
                getAllCities.addAll(getItems(i, step));
            }
            LOGGER.debug("Call getItems. Added step increment");

            session.getTransaction().commit();
            LOGGER.debug("fetchData. Commit transaction");
            return getAllCities;
        }
    }

    public void testMysqlData(List<Integer> ids) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            LOGGER.debug("testMysqlData. Start transaction");

            for (Integer id : ids) {
                City city = getById(id);
                Set<CountryLanguage> languages = city.getCountry().getLanguages();
            }

            LOGGER.debug("Call getById");

            session.getTransaction().commit();
            LOGGER.debug("testMysqlData. Commit transaction");
        }
    }
}
