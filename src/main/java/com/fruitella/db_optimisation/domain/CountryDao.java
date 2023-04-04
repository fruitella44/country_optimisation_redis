package com.fruitella.db_optimisation.domain;

import com.fruitella.db_optimisation.entity.Country;

import java.util.List;

public interface CountryDao {
    public List<Country> getAll();
}
