package com.fruitella.db_optimisation.service;

import com.fruitella.db_optimisation.DAO.CountryDaoIml;
import com.fruitella.db_optimisation.connection.DbConnection;
import com.fruitella.db_optimisation.dbEntity.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import java.util.List;


public class CountryService {

    private static final Logger LOGGER = LogManager.getLogger(CountryService.class);
    private final CountryDaoIml countryDao;

    public CountryService() {
        SessionFactory sessionFactory = DbConnection.getSessionFactory();
        countryDao = new CountryDaoIml(sessionFactory);
        LOGGER.debug("Initialized session: [sessionFactory, countryDao]");
    }

    public List<Country> getAll() {
        LOGGER.debug("Call countryDao getAll");
        return countryDao.getAll();
    }
}
