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


class StaffControllerIntegrationTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/api/v1/staff";
    private static Integer staffId;

    @Order(1)
    @Test
    void create() {
        String firstName = "Test first name";
        String lastName = "Test last name";
        Integer addressId = 1;
        String picture = "picture";
        String email = "test@example.com";
        Integer storeId = 1;
        String username = "testusername";
        String password = "<PASSWORD>";

        StaffCreateRequest request = StaffCreateRequest.builder()
                .storeId(storeId)
                .firstName(firstName)
                .lastName(lastName)
                .addressId(addressId)
                .picture(picture)
                .email(email)
                .storeId(storeId)
                .username(username)
                .password(password)
                .build();

        ResponseEntity<StaffFindByIdResponse> responseEntity = restTemplate.postForEntity(BASE_URL + "/create", request, StaffFindByIdResponse.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        StaffFindByIdResponse expectedResponse = StaffFindByIdResponse.builder()
                .staffDto(StaffDto.builder()
                        .storeId(storeId)
                        .firstName(firstName)
                        .lastName(lastName)
                        .picture(picture)
                        .email(email)
                        .storeId(storeId)
                        .username(username)
                        .password(password)
                        .active(true)
                        .addressDto(null)
                        .build())
                .build();
        StaffFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        StaffDto dto = body.getStaffDto();
        assertThat(dto.getStaffId()).isNotNull();
        assertThat(dto.getActive()).isTrue();
        assertThat(dto).usingRecursiveComparison().ignoringCollectionOrder().ignoringFields("staffId", "addressDto").isEqualTo(expectedResponse.getStaffDto());
        staffId = dto.getStaffId();
    }

    @Order(2)
    @Test
    void findById() {
        ResponseEntity<StaffFindByIdResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findById?id={id}",
                StaffFindByIdResponse.class, staffId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        StaffFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(staffId, body.getStaffDto().getStaffId());
    }

    @Order(3)
    @Test
    void update() {
        String firstName = "Test firstName update";
        String lastName = "Test lastName update";
        Integer addressId = 1;
        String picture = "picture_updated";
        String email = "test@example.com";
        Integer storeId = 1;
        Boolean active = false;
        String username = "testusernameupdate";
        String password = "<PASSWORDUPDATED>";
        StaffUpdateRequest request = StaffUpdateRequest.builder()
                .staffId(staffId)
                .storeId(storeId)
                .firstName(firstName)
                .lastName(lastName)
                .addressId(addressId)
                .picture(picture)
                .email(email)
                .storeId(storeId)
                .active(active)
                .username(username)
                .password(password)
                .build();
        HttpEntity<StaffUpdateRequest> httpEntity = new HttpEntity<>(request);

        ResponseEntity<StaffFindByIdResponse> responseEntity = restTemplate.exchange(BASE_URL + "/update", HttpMethod.PUT, httpEntity, StaffFindByIdResponse.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        StaffFindByIdResponse expectedResponse = StaffFindByIdResponse.builder()
                .staffDto(StaffDto.builder()
                        .staffId(staffId)
                        .storeId(storeId)
                        .firstName(firstName)
                        .lastName(lastName)
                        .picture(picture)
                        .email(email)
                        .storeId(storeId)
                        .username(username)
                        .password(password)
                        .active(active)
                        .addressDto(null)
                        .build())
                .build();
        StaffFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        StaffDto dto = body.getStaffDto();
        assertThat(dto.getAddressDto()).isNotNull();
        assertThat(dto).usingRecursiveComparison().ignoringCollectionOrder().ignoringFields("addressDto").isEqualTo(expectedResponse.getStaffDto());
        staffId = dto.getStaffId();
    }

    @Order(4)
    @Test
    void findAll() {
        ResponseEntity<StaffFindAllResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findAll", StaffFindAllResponse.class);
        StaffFindAllResponse body = responseEntity.getBody();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(body);
        assertThat(body.getStaffDtos()).hasSizeGreaterThanOrEqualTo(1);

    }

    @Order(5)
    @Test
    void delete() {
        ResponseEntity<StaffRemoveResponse> responseEntity = restTemplate.exchange(BASE_URL + "/delete?id={id}",
                HttpMethod.DELETE, null, StaffRemoveResponse.class, staffId);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
