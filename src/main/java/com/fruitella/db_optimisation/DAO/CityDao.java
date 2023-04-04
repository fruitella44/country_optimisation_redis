package com.fruitella.db_optimisation.DAO;

import com.fruitella.db_optimisation.entity.City;

import java.util.List;

public interface CityDao {
    public List<City> getItems(int offset, int limit);
    public int getTotalCount();
    public List<City> fetchData(int step);
    public City getById(Integer cityId);
}
