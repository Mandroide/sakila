package com.sakila.integration;

import com.sakila.common.AbstractIntegrationTest;
import com.sakila.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class RentalControllerIntegrationTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/api/v1/rentals";
    private static final List<Integer> rentalIds = new ArrayList<>();

    @Order(1)
    @RepeatedTest(2)
    void create() {
        Random rand = new Random();
        int n = rand.nextInt(15);
        Integer customerId = 1;
        Integer staffId = 2;
        Integer inventoryId = 3;
        LocalDateTime rentalDate = LocalDateTime.now().plusHours(n);
        LocalDateTime returnDate = rentalDate.plusDays(7);
        RentalCreateRequest request = RentalCreateRequest.builder()
                .rentalDate(rentalDate)
                .inventoryId(inventoryId)
                .customerId(customerId)
                .returnDate(returnDate)
                .staffId(staffId)
                .build();

        ResponseEntity<RentalFindByIdResponse> responseEntity = restTemplate.postForEntity(BASE_URL + "/create", request, RentalFindByIdResponse.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        RentalFindByIdResponse expectedResponse = RentalFindByIdResponse.builder()
                .rentalDto(RentalDto.builder()
                        .rentalId(null)
                        .rentalDate(rentalDate)
                        .inventoryId(inventoryId)
                        .customerId(customerId)
                        .returnDate(returnDate)
                        .staffId(staffId)
                        .build())
                .build();
        RentalFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        RentalDto dto = body.getRentalDto();
        assertThat(dto.getRentalId()).isNotNull();
        assertThat(dto.getRentalDate()).isNotNull();
        assertThat(dto).usingRecursiveComparison().ignoringCollectionOrder()
                .ignoringFields("rentalId").isEqualTo(expectedResponse.getRentalDto());
        rentalIds.add(dto.getRentalId());
    }

    @Order(2)
    @Test
    void findById() {
        Integer rentalId = rentalIds.getFirst();
        ResponseEntity<RentalFindByIdResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findById?id={id}",
                RentalFindByIdResponse.class, rentalId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        RentalFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(rentalId, body.getRentalDto().getRentalId());
    }

    @Order(4)
    @Test
    void findAll() {
        ResponseEntity<RentalFindAllResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findAll", RentalFindAllResponse.class);
        RentalFindAllResponse body = responseEntity.getBody();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(body);
        assertThat(body.getRentalDtos()).hasSizeGreaterThanOrEqualTo(1);

    }

    @Order(5)
    @Test
    void delete() {
        HttpEntity<RentalRemoveRequest> request = new HttpEntity<>(RentalRemoveRequest.builder().ids(Set.copyOf(rentalIds)).build());
        ResponseEntity<RentalRemoveResponse> responseEntity = restTemplate.exchange(BASE_URL + "/delete",
                HttpMethod.DELETE, request, RentalRemoveResponse.class);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
