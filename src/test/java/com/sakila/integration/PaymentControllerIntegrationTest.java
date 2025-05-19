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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class PaymentControllerIntegrationTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/api/v1/payments";
    private static final List<Integer> paymentIds = new ArrayList<>();

    @Order(1)
    @RepeatedTest(2)
    void create() {
        Integer customerId = 1;
        Integer staffId = 2;
        Integer rentalId = 3;
        BigDecimal amount = BigDecimal.valueOf(4.99);
        PaymentCreateRequest request = PaymentCreateRequest.builder()
                .customerId(customerId)
                .staffId(staffId)
                .rentalId(rentalId)
                .amount(amount)
                .build();

        ResponseEntity<PaymentFindByIdResponse> responseEntity = restTemplate.postForEntity(BASE_URL + "/create", request, PaymentFindByIdResponse.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        PaymentFindByIdResponse expectedResponse = PaymentFindByIdResponse.builder()
                .paymentDto(PaymentDto.builder()
                        .paymentId(null)
                        .customerId(customerId)
                        .staffId(staffId)
                        .rentalId(rentalId)
                        .amount(amount)
                        .paymentDate(null)
                        .build())
                .build();
        PaymentFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        PaymentDto dto = body.getPaymentDto();
        assertThat(dto.getPaymentId()).isNotNull();
        assertThat(dto.getPaymentDate()).isNotNull();
        assertThat(dto).usingRecursiveComparison().ignoringCollectionOrder()
                .ignoringFields("paymentId", "paymentDate").isEqualTo(expectedResponse.getPaymentDto());
        paymentIds.add(dto.getPaymentId());
    }

    @Order(2)
    @Test
    void findById() {
        Integer paymentId = paymentIds.getFirst();
        ResponseEntity<PaymentFindByIdResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findById?id={id}",
                PaymentFindByIdResponse.class, paymentId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        PaymentFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(paymentId, body.getPaymentDto().getPaymentId());
    }

    @Order(4)
    @Test
    void findAll() {
        ResponseEntity<PaymentFindAllResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findAll", PaymentFindAllResponse.class);
        PaymentFindAllResponse body = responseEntity.getBody();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(body);
        assertThat(body.getPayments()).hasSizeGreaterThanOrEqualTo(1);

    }

    @Order(5)
    @Test
    void delete() {
        HttpEntity<PaymentRemoveRequest> request = new HttpEntity<>(PaymentRemoveRequest.builder().ids(Set.copyOf(paymentIds)).build());
        ResponseEntity<PaymentRemoveResponse> responseEntity = restTemplate.exchange(BASE_URL + "/delete",
                HttpMethod.DELETE, request, PaymentRemoveResponse.class);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
