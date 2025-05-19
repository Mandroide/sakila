package com.sakila.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.sakila.enums.Category;
import com.sakila.enums.Language;
import com.sakila.enums.Rating;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FilmUpdateRequest {
    @Min(1)
    @NotNull
    private Integer filmId;
    @NotBlank
    @Size(min = 1, max = 128)
    private String title;
    private String description;
    private Year releaseYear;
    private Language language;
    private Language originalLanguage;
    private Integer rentalDuration;
    @NotNull
    @Digits(integer = 4, fraction = 2)
    private BigDecimal rentalRate;
    private Integer length;
    @NotNull
    @Digits(integer = 5, fraction = 2)
    private BigDecimal replacementCost;
    private Rating rating;
    private String specialFeatures;
    @Builder.Default
    @JsonSetter(nulls = Nulls.SKIP)
    private Set<@NotNull Category> categories = new HashSet<>();
    @Builder.Default
    @JsonSetter(nulls = Nulls.SKIP)
    private Set<@NotNull Integer> actorIds = new HashSet<>();
}
