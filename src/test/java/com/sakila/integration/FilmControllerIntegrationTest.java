package com.sakila.integration;

import com.sakila.common.AbstractIntegrationTest;
import com.sakila.dto.*;
import com.sakila.enums.Category;
import com.sakila.enums.Language;
import com.sakila.enums.Rating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class FilmControllerIntegrationTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/api/v1/films";
    private static Integer filmId;

    @Order(1)
    @Test
    void create() {
        String title = "Test film";
        String description = "Test film description";
        Year releaseYear = Year.of(2020);
        Language language = Language.ENGLISH;
        Language originalLanguage = Language.ENGLISH;
        Integer rentalDuration = 3;
        BigDecimal rentalRate = BigDecimal.valueOf(4.99);
        Integer length = 120;
        BigDecimal replacementCost = BigDecimal.valueOf(19.99);
        Rating rating = Rating.G;
        String specialFeatures = "Deleted Scenes";
        Set<Category> categories = Set.of(Category.ACTION, Category.COMEDY);
        Set<Integer> actorIds = Set.of();
        FilmCreateRequest request = FilmCreateRequest.builder()
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
                .categories(categories)
                .actorIds(actorIds)
                .build();

        ResponseEntity<FilmFindByIdResponse> responseEntity = restTemplate.postForEntity(BASE_URL + "/create", request, FilmFindByIdResponse.class);

        FilmFindByIdResponse expectedResponse = FilmFindByIdResponse.builder()
                .film(FilmDto.builder()
                        .filmId(null)
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
                        .categories(categories.stream().toList())
                        .build())
                .build();

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        FilmFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        FilmDto dto = body.getFilm();
        assertThat(dto.getFilmId()).isNotNull();
        assertThat(dto).usingRecursiveComparison().ignoringCollectionOrder().ignoringFields("filmId", "actors").isEqualTo(expectedResponse.getFilm());
        filmId = dto.getFilmId();
    }

    @Order(2)
    @Test
    void findById() {
        ResponseEntity<FilmFindByIdResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findById?id={id}",
                FilmFindByIdResponse.class, filmId);

        FilmFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(filmId, body.getFilm().getFilmId());
    }

    @Order(3)
    @Test
    void update() {
        String title = "Test film updated";
        String description = "Test film description updated";
        Year releaseYear = Year.of(2021);
        Language language = Language.ENGLISH;
        Language originalLanguage = Language.GERMAN;
        Integer rentalDuration = 3;
        BigDecimal rentalRate = BigDecimal.valueOf(4.99);
        Integer length = 120;
        BigDecimal replacementCost = BigDecimal.valueOf(19.99);
        Rating rating = Rating.G;
        String specialFeatures = "Deleted Scenes";
        Set<Category> categories = Set.of(Category.ACTION, Category.COMEDY, Category.ANIMATION);
        Set<Integer> actorIds = Set.of();
        FilmUpdateRequest request = FilmUpdateRequest.builder()
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
                .categories(categories)
                .actorIds(actorIds)
                .build();
        HttpEntity<FilmUpdateRequest> httpEntity = new HttpEntity<>(request);

        ResponseEntity<FilmFindByIdResponse> responseEntity = restTemplate.exchange(BASE_URL + "/update", HttpMethod.PUT, httpEntity, FilmFindByIdResponse.class);

        FilmFindByIdResponse expectedResponse = FilmFindByIdResponse.builder()
                .film(FilmDto.builder()
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
                        .categories(categories.stream().toList())
                        .build())
                .build();
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        FilmFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        FilmDto dto = body.getFilm();
        assertThat(dto).usingRecursiveComparison().ignoringCollectionOrder().ignoringFields("actors").isEqualTo(expectedResponse.getFilm());
        filmId = dto.getFilmId();
    }

    @Order(4)
    @Test
    void findAll() {

        ResponseEntity<FilmFindAllResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findAll", FilmFindAllResponse.class);
        FilmFindAllResponse body = responseEntity.getBody();

        Assertions.assertNotNull(body);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(body.getFilms()).hasSizeGreaterThanOrEqualTo(1);

    }

    @Order(5)
    @Test
    void delete() {
        ResponseEntity<FilmRemoveResponse> responseEntity = restTemplate.exchange(BASE_URL + "/delete?id={id}",
                HttpMethod.DELETE, null, FilmRemoveResponse.class, filmId);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
