package com.fruitella.db_optimisation.dbEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "city", schema = "world")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "district")
    private String district;

    @Column(name = "population")
    private Integer population;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
