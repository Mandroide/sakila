package com.sakila.entity;

import com.sakila.converter.LanguageConverter;
import com.sakila.converter.RatingConverter;
import com.sakila.dto.CategoryDto;
import com.sakila.dto.FilmDto;
import com.sakila.enums.Language;
import com.sakila.enums.Rating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;

@Table(name = "film")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FilmEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "film_id")
    private Integer filmId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "release_year")
    private Year releaseYear;
    @Convert(converter = LanguageConverter.class)
    @Column(name = "language_id")
    private Language language;
    @Convert(converter = LanguageConverter.class)
    @Column(name = "original_language_id")
    private Language originalLanguage;
    @Column(name = "rental_duration")
    private Integer rentalDuration;
    @Column(name = "rental_rate")
    private BigDecimal rentalRate;
    @Column(name = "length")
    private Integer length;
    @Column(name = "replacement_cost")
    private BigDecimal replacementCost;
    @Convert(converter = RatingConverter.class)
    @Column(name = "rating")
    private Rating rating;
    @Column(name = "special_features")
    private String specialFeatures;
    @Column(name = "last_update", insertable = false)
    private LocalDateTime lastUpdate;

    @PreUpdate
    private void beforeUpdate() {
        setLastUpdate(LocalDateTime.now());
    }

    public FilmDto toDto() {
        return FilmDto.builder()
                .filmId(filmId)
                .title(title)
                .description(description)
                .releaseYear(releaseYear)
                .language(language)
                .originalLanguage(originalLanguage)
                .rentalDuration(rentalDuration)
                .rentalRate(rentalRate)
                .length(length)
                .replacementCost(replacementCost)
                .rating(rating)
                .specialFeatures(specialFeatures)
                .build();
    }
}
