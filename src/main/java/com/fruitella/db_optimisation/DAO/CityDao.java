package com.fruitella.db_optimisation.DAO;

import com.fruitella.db_optimisation.dbEntity.City;

import java.util.List;

public interface CityDao {
    public List<City> getItems(int offset, int limit);
    public int getTotalCount();
    public City getById(Integer cityId);
}
