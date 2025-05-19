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


class CustomerControllerIntegrationTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/api/v1/addresses";
    private static Integer customerId;

    @Order(1)
    @Test
    void create() {
        Integer storeId = 1;
        String firstName = "Test first name";
        String lastName = "Test last name";
        String email = "test@example.com";
        Integer addressId = 1;
        CustomerCreateRequest request = CustomerCreateRequest.builder()
                .storeId(storeId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .addressId(addressId)
                .build();

        ResponseEntity<CustomerFindByIdResponse> responseEntity = restTemplate.postForEntity(BASE_URL + "/create", request, CustomerFindByIdResponse.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        CustomerFindByIdResponse expectedResponse = CustomerFindByIdResponse.builder()
                .customerDto(CustomerDto.builder()
                        .storeId(storeId)
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .active(true)
                        .addressDto(null)
                        .createDate(null)
                        .build())
                .build();
        CustomerFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        CustomerDto dto = body.getCustomerDto();
        assertThat(dto.getCustomerId()).isNotNull();
        assertThat(dto.getActive()).isTrue();
        assertThat(dto).usingRecursiveComparison().ignoringCollectionOrder().ignoringFields("customerId", "addressDto", "createDate", "active").isEqualTo(expectedResponse.getCustomerDto());
        customerId = dto.getCustomerId();
    }

    @Order(2)
    @Test
    void findById() {
        ResponseEntity<CustomerFindByIdResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findById?id={id}",
                CustomerFindByIdResponse.class, customerId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CustomerFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(customerId, body.getCustomerDto().getCustomerId());
    }

    @Order(3)
    @Test
    void update() {
        Integer storeId = 2;
        String firstName = "Test first name updated";
        String lastName = "Test last name update";
        String email = "testupdated@example.com";
        Integer addressId = 1;
        Boolean active = false;
        CustomerUpdateRequest request = CustomerUpdateRequest.builder()
                .customerId(customerId)
                .storeId(storeId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .addressId(addressId)
                .active(active)
                .build();
        HttpEntity<CustomerUpdateRequest> httpEntity = new HttpEntity<>(request);

        ResponseEntity<CustomerFindByIdResponse> responseEntity = restTemplate.exchange(BASE_URL + "/update", HttpMethod.PUT, httpEntity, CustomerFindByIdResponse.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CustomerFindByIdResponse expectedResponse = CustomerFindByIdResponse.builder()
                .customerDto(CustomerDto.builder()
                        .customerId(customerId)
                        .storeId(storeId)
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .active(active)
                        .addressDto(null)
                        .build())
                .build();
        CustomerFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        CustomerDto dto = body.getCustomerDto();
        assertThat(dto.getAddressDto()).isNotNull();
        assertThat(dto).usingRecursiveComparison().ignoringCollectionOrder().ignoringFields("addressDto").isEqualTo(expectedResponse.getCustomerDto());
        customerId = dto.getCustomerId();
    }

    @Order(4)
    @Test
    void findAll() {

        ResponseEntity<CustomerFindAllResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findAll", CustomerFindAllResponse.class);
        CustomerFindAllResponse body = responseEntity.getBody();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(body);
        assertThat(body.getCustomers()).hasSizeGreaterThanOrEqualTo(1);

    }

    @Order(5)
    @Test
    void delete() {
        ResponseEntity<CustomerRemoveResponse> responseEntity = restTemplate.exchange(BASE_URL + "/delete?id={id}",
                HttpMethod.DELETE, null, CustomerRemoveResponse.class, customerId);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
