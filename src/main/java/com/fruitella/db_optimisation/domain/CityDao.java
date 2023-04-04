package com.fruitella.db_optimisation.domain;

import com.fruitella.db_optimisation.entity.City;

import java.util.List;

public interface CityDao {
    public List<City> getItems(int offset, int limit);
    public int getTotalCount();
    public List<City> fetchData(int step);
}
