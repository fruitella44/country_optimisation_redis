package com.fruitella.db_optimisation.redis;

import com.fruitella.db_optimisation.entity.City;
import com.fruitella.db_optimisation.entity.Country;
import com.fruitella.db_optimisation.entity.CountryLanguage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public class SendData {
    private CityCountry res;
    private Country country;
    private Set<CountryLanguage> countryLanguages;
    private Set<Language> languages;



}
