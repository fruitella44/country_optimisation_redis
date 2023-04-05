package com.fruitella.db_optimisation.DAO;

import com.fruitella.db_optimisation.dbEntity.City;
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
public class CityDaoIml implements CityDao {
    private static final Logger LOGGER = LogManager.getLogger(CityDaoIml.class);
    private SessionFactory sessionFactory;

    @Override
    public List<City> getItems(int offset, int limit) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            LOGGER.debug("getAllItems. Start transaction");

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<City> criteriaQuery = builder.createQuery(City.class);

            Root<City> root = criteriaQuery.from(City.class);
            criteriaQuery.select(root);

            Query<City> cityQuery = session.createQuery(criteriaQuery);
            cityQuery.setFirstResult(offset);
            cityQuery.setMaxResults(limit);

            session.getTransaction().commit();
            LOGGER.debug("getAllItems. Commit transaction");
            return cityQuery.list();
        }
    }

    @Override
    public int getTotalCount() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            LOGGER.debug("getTotalCount. Start transaction");

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> queryCount = criteriaBuilder.createQuery(Long.class);

            Root<City> root = queryCount.from(City.class);
            queryCount.select(criteriaBuilder.count(root));

            Query<Long> getCount = session.createQuery(queryCount);

            session.getTransaction().commit();
            LOGGER.debug("Get totalCount. Commit transaction");
            return Math.toIntExact(getCount.uniqueResult());
        }
    }

    @Override
    public City getById(Integer cityId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            LOGGER.debug("getById. Start transaction");

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<City> criteriaQuery = builder.createQuery(City.class);

            Root<City> root = criteriaQuery.from(City.class);
            root.fetch("country", JoinType.INNER);
            criteriaQuery.select(root).where(builder.equal(root.get("id"), cityId));

            Query<City> queryGetId = session.createQuery(criteriaQuery);
            City city = queryGetId.getSingleResult();

            session.getTransaction().commit();
            LOGGER.debug("getById. Commit transaction");
            return city;
        }
    }

}
