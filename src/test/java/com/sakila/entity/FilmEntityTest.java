package com.sakila.entity;

import com.sakila.common.AbstractJpaTest;
import com.sakila.enums.Language;
import com.sakila.enums.Rating;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FilmEntityTest extends AbstractJpaTest {

    @Test
    void persistFlushFind() {
        FilmEntity expected = FilmEntity.builder()
                .filmId(null)
                .title("Test film")
                .description("Test film description")
                .releaseYear(Year.of(2020))
                .language(Language.ENGLISH)
                .originalLanguage(Language.ENGLISH)
                .rentalDuration(3)
                .rentalRate(BigDecimal.valueOf(4.99))
                .length(120)
                .replacementCost(BigDecimal.valueOf(19.99))
                .rating(Rating.G)
                .specialFeatures("Deleted Scenes")
                .lastUpdate(null)
                .build();

        FilmEntity actual = entityManager.persistFlushFind(expected);
        assertNotNull(actual);
        assertNotNull(actual.getFilmId());
        assertNotNull(actual.getLastUpdate());
        assertThat(actual).usingRecursiveComparison().ignoringFields("filmId", "lastUpdate", "filmCategories").isEqualTo(expected);
    }
}