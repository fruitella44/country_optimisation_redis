package com.fruitella.db_optimisation.service;

import com.fruitella.db_optimisation.DAO.CityDaoIml;
import com.fruitella.db_optimisation.connection.DbConnection;
import com.fruitella.db_optimisation.dbEntity.City;
import com.fruitella.db_optimisation.dbEntity.CountryLanguage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class CityService {
    private final CityDaoIml cityDao;
    private final SessionFactory sessionFactory;

    public CityService() {
        sessionFactory = DbConnection.getSessionFactory();
        cityDao = new CityDaoIml(sessionFactory);
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
            Transaction transaction = session.beginTransaction();
            int totalCount = getTotalCount();

            for (int i = 0; i < totalCount; i += step) {
                getAllCities.addAll(getItems(i, step));
            }

            transaction.commit();
            return getAllCities;
        }
    }

    public void testMysqlData(List<Integer> ids) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            for (Integer id : ids) {
                City city = getById(id);
                Set<CountryLanguage> languages = city.getCountry().getLanguages();
            }

           transaction.commit();
        }
    }
}
