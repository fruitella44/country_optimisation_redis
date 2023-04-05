package com.fruitella.db_optimisation.DAO;

import com.fruitella.db_optimisation.dbEntity.Country;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

@AllArgsConstructor
public class CountryDaoIml implements CountryDao {
    private static final Logger LOGGER = LogManager.getLogger(CountryDaoIml.class);
    private SessionFactory sessionFactory;

    @Override
    public List<Country> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            LOGGER.debug("getAll. Start transaction");

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Country> query = criteriaBuilder.createQuery(Country.class);

            Root<Country> root = query.from(Country.class);
            root.fetch("languages", JoinType.LEFT);
            query.select(root);

            Query<Country> countryQuery = session.createQuery(query);

            session.getTransaction().commit();
            LOGGER.debug("getAll. Commit transaction");
            return countryQuery.list();
        }

    }
}
