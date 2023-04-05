package com.fruitella.db_optimisation.redisEntity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Language {
    private String language;
    private Boolean isOfficial;
    private BigDecimal percentage;
}
