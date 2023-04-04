package com.fruitella.db_optimisation.service;

import com.fruitella.db_optimisation.DAO.CityDaoIml;
import com.fruitella.db_optimisation.connection.DbConnection;
import com.fruitella.db_optimisation.entity.City;
import com.fruitella.db_optimisation.entity.CountryLanguage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Set;


public class CityService {
    private final CityDaoIml cityDao;
    private final SessionFactory sessionFactory;

    public CityService() {
        sessionFactory = DbConnection.getSessionFactory();
        cityDao = new CityDaoIml(sessionFactory);
    }

    public List<City> getItems(int offset, int limit) {
        return cityDao.getItems(offset, limit);
    }

    public int getTotalCount() {
        return cityDao.getTotalCount();
    }

    public City getById(Integer cityId) {
        return cityDao.getById(cityId);
    }

    public List<City> fetchData(int step) {
        return cityDao.fetchData(step);
    }

    public void testMysqlData(List<Integer> ids) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            for (Integer id : ids) {
                City city = cityDao.getById(id);
                Set<CountryLanguage> languages = city.getCountry().getLanguages();
            }

           transaction.commit();
        }
    }
}
