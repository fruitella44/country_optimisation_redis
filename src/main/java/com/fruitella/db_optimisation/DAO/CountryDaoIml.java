package com.fruitella.db_optimisation.DAO;

import com.fruitella.db_optimisation.dbEntity.City;
import com.fruitella.db_optimisation.dbEntity.Country;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
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
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Country> query = criteriaBuilder.createQuery(Country.class);

            Root<Country> root = query.from(Country.class);
            root.fetch("languages", JoinType.INNER);
            query.select(root);

            Query<Country> countryQuery = session.createQuery(query);

            session.getTransaction().commit();
            return countryQuery.list();
        }

    }
}
