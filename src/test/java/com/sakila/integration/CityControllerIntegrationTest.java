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


class CityControllerIntegrationTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/api/v1/cities";
    private static Integer cityId;

    @Order(1)
    @Test
    void create() {
        String city = "Test city";
        Integer countryId = 1;
        CityCreateRequest request = CityCreateRequest.builder()
                .city(city)
                .countryId(countryId)
                .build();

        ResponseEntity<CityFindByIdResponse> responseEntity = restTemplate.postForEntity(BASE_URL + "/create", request, CityFindByIdResponse.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        CityFindByIdResponse expectedResponse = CityFindByIdResponse.builder()
                .cityDto(CityDto.builder()
                        .city(city)
                        .country(null)
                        .build())
                .build();
        CityFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        CityDto dto = body.getCityDto();
        assertThat(dto.getCityId()).isNotNull();
        assertThat(dto).usingRecursiveComparison().ignoringCollectionOrder().ignoringFields("cityId", "country").isEqualTo(expectedResponse.getCityDto());
        cityId = dto.getCityId();
    }

    @Order(2)
    @Test
    void findById() {
        ResponseEntity<CityFindByIdResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findById?id={id}",
                CityFindByIdResponse.class, cityId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CityFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(cityId, body.getCityDto().getCityId());
    }

    @Order(3)
    @Test
    void update() {
        String city = "Test city updated";
        Integer countryId = 2;
        CityUpdateRequest request = CityUpdateRequest.builder()
                .cityId(cityId)
                .city(city)
                .countryId(countryId)
                .build();
        HttpEntity<CityUpdateRequest> httpEntity = new HttpEntity<>(request);
        ResponseEntity<CityFindByIdResponse> responseEntity = restTemplate.exchange(BASE_URL + "/update", HttpMethod.PUT, httpEntity, CityFindByIdResponse.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CityFindByIdResponse expectedResponse = CityFindByIdResponse.builder()
                .cityDto(CityDto.builder()
                        .cityId(cityId)
                        .city(city)
                        .country(null)
                        .build())
                .build();
        CityFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        CityDto dto = body.getCityDto();
        assertThat(dto).usingRecursiveComparison().ignoringCollectionOrder().ignoringFields("country").isEqualTo(expectedResponse.getCityDto());
        CityControllerIntegrationTest.cityId = dto.getCityId();
    }

    @Order(4)
    @Test
    void findAll() {

        ResponseEntity<CityFindAllResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findAll", CityFindAllResponse.class);
        CityFindAllResponse body = responseEntity.getBody();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(body);
        assertThat(body.getCities()).hasSizeGreaterThanOrEqualTo(1);

    }

    @Order(5)
    @Test
    void delete() {
        ResponseEntity<CityRemoveResponse> responseEntity = restTemplate.exchange(BASE_URL + "/delete?id={id}",
                HttpMethod.DELETE, null, CityRemoveResponse.class, cityId);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
