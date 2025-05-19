package com.sakila.dto;

import com.sakila.enums.Category;
import com.sakila.enums.Language;
import com.sakila.enums.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FilmDto {
    private Integer filmId;
    private String title;
    private String description;
    private Year releaseYear;
    private Language language;
    private Language originalLanguage;
    private Integer rentalDuration;
    private BigDecimal rentalRate;
    private Integer length;
    private BigDecimal replacementCost;
    private Rating rating;
    private String specialFeatures;
    private List<Category> categories;
    private List<ActorDto> actors;
}
