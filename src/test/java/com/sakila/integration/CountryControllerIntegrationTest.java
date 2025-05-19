package com.sakila.integration;

import com.sakila.common.AbstractIntegrationTest;
import com.sakila.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


class CountryControllerIntegrationTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/api/v1/countries";
    private static Integer countryId;

    @Order(1)
    @Test
    void create() {
        String country = "Test country";
        CountryCreateRequest request = CountryCreateRequest.builder()
                .country(country)
                .build();

        ResponseEntity<CountryFindByIdResponse> responseEntity = restTemplate.postForEntity(BASE_URL + "/create", request, CountryFindByIdResponse.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        CountryFindByIdResponse expectedResponse = CountryFindByIdResponse.builder()
                .countryDto(CountryDto.builder()
                        .country(country)
                        .build())
                .build();
        CountryFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        CountryDto dto = body.getCountryDto();
        assertThat(dto.getCountryId()).isNotNull();
        assertThat(dto).usingRecursiveComparison().ignoringCollectionOrder().ignoringFields("countryId").isEqualTo(expectedResponse.getCountryDto());
        countryId = dto.getCountryId();
    }

    @Order(2)
    @Test
    void findById() {
        ResponseEntity<CountryFindByIdResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findById?id={id}",
                CountryFindByIdResponse.class, countryId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CountryFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(countryId, body.getCountryDto().getCountryId());
    }

    @Order(3)
    @Test
    void update() {
        String country = "Test country updated";
        CountryUpdateRequest request = CountryUpdateRequest.builder()
                .countryId(countryId)
                .country(country)
                .build();
        HttpEntity<CountryUpdateRequest> httpEntity = new HttpEntity<>(request);

        ResponseEntity<CountryFindByIdResponse> responseEntity = restTemplate.exchange(BASE_URL + "/update", HttpMethod.PUT, httpEntity, CountryFindByIdResponse.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CountryFindByIdResponse expectedResponse = CountryFindByIdResponse.builder()
                .countryDto(CountryDto.builder()
                        .countryId(countryId)
                        .country(country)
                        .build())
                .build();
        CountryFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        CountryDto dto = body.getCountryDto();
        assertThat(dto).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedResponse.getCountryDto());
        countryId = dto.getCountryId();
    }

    @Order(4)
    @Test
    void findAll() {

        ResponseEntity<CountryFindAllResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findAll", CountryFindAllResponse.class);
        CountryFindAllResponse body = responseEntity.getBody();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(body);
        assertThat(body.getCountries()).hasSizeGreaterThanOrEqualTo(1);

    }

    @Order(5)
    @Test
    void delete() {
        ResponseEntity<CountryRemoveResponse> responseEntity = restTemplate.exchange(BASE_URL + "/delete?id={id}",
                HttpMethod.DELETE, null, CountryRemoveResponse.class, countryId);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
