package com.fruitella.db_optimisation.connection;

import com.fruitella.db_optimisation.entity.City;
import com.fruitella.db_optimisation.entity.Country;
import com.fruitella.db_optimisation.entity.CountryLanguage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DbConnectionSessionFactory {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Country.class);
            configuration.addAnnotatedClass(City.class);
            configuration.addAnnotatedClass(CountryLanguage.class);
            configuration.configure();

            return configuration.buildSessionFactory();
        } catch (Throwable exception) {
            throw new ExceptionInInitializerError("Connection false " + exception);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
