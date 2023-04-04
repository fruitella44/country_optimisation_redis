package com.fruitella.db_optimisation.service;

import com.fruitella.db_optimisation.DAO.CountryDaoIml;
import com.fruitella.db_optimisation.connection.DbConnection;
import com.fruitella.db_optimisation.entity.Country;
import org.hibernate.SessionFactory;

import java.util.List;


public class CountryService {
    private final SessionFactory sessionFactory;
    private final CountryDaoIml countryDao;

    public CountryService() {
        sessionFactory = DbConnection.getSessionFactory();
        countryDao = new CountryDaoIml(sessionFactory);
    }

    public List<Country> getAll() {
        return countryDao.getAll();
    }
}
