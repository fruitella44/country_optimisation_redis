package com.fruitella.db_optimisation.domain;

import com.fruitella.db_optimisation.entity.Country;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@AllArgsConstructor
public class CountryDaoIml implements CountryDao {

    private SessionFactory sessionFactory;

    @Override
    public List<Country> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Country> query = session.createQuery("select c from Country c", Country.class);

            transaction.commit();
            return query.list();
        }
    }
}
