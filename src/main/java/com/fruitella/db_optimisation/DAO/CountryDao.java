package com.fruitella.db_optimisation.DAO;

import com.fruitella.db_optimisation.dbEntity.Country;

import java.util.List;

public interface CountryDao {
    public List<Country> getAll();
}
