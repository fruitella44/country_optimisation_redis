package com.fruitella.db_optimisation.connection;

import com.fruitella.db_optimisation.dbEntity.City;
import com.fruitella.db_optimisation.dbEntity.Country;
import com.fruitella.db_optimisation.dbEntity.CountryLanguage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DbConnection {
    private static final Logger LOGGER = LogManager.getLogger(DbConnection.class);
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Country.class);
            configuration.addAnnotatedClass(City.class);
            configuration.addAnnotatedClass(CountryLanguage.class);
            configuration.configure();

            LOGGER.debug("Connect to mysqlDb - hibernate.cfg.xml. Mapping classes: [Country, City, CountryLanguage]");
            return configuration.buildSessionFactory();
        } catch (Throwable exception) {
            LOGGER.error("Connection fail " + exception);
            throw new ExceptionInInitializerError(exception);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
