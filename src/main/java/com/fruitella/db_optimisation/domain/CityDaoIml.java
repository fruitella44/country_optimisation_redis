package com.fruitella.db_optimisation.domain;

import com.fruitella.db_optimisation.entity.City;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CityDaoIml implements CityDao {

    private final SessionFactory sessionFactory;

    @Override
    public List<City> getItems(int offset, int limit) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<City> query = session.createQuery("select c from City c", City.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);

            transaction.commit();
            return query.list();
        }
    }

    @Override
    public int getTotalCount() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Long> query = session.createQuery("select count(c) from City c", Long.class);

            transaction.commit();
            return Math.toIntExact(query.uniqueResult());
        }
    }

    @Override
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
}
