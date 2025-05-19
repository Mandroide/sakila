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

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class ActorControllerIntegrationTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/api/v1/actors";
    private static Integer actorId;

    @Order(1)
    @Test
    void create() {
        String firstName = "Test actor";
        String lastName = "Test actor last name";
        ActorCreateRequest request = ActorCreateRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .filmIds(Set.of(1, 2))
                .build();

        ResponseEntity<ActorFindByIdResponse> responseEntity = restTemplate.postForEntity(BASE_URL + "/create", request, ActorFindByIdResponse.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        ActorFindByIdResponse expectedResponse = ActorFindByIdResponse.builder()
                .actor(ActorDto.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .build())
                .build();
        ActorFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        ActorDto dto = body.getActor();
        assertThat(dto.getActorId()).isNotNull();
        assertThat(dto).usingRecursiveComparison().ignoringCollectionOrder().ignoringFields("actorId").isEqualTo(expectedResponse.getActor());
        actorId = dto.getActorId();
    }

    @Order(2)
    @Test
    void findById() {
        ResponseEntity<ActorFindByIdResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findById?id={id}",
                ActorFindByIdResponse.class, actorId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ActorFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(actorId, body.getActor().getActorId());
    }

    @Order(3)
    @Test
    void update() {
        String firstName = "Test actor updated";
        String lastName = "Test actor last name updated";
        ActorUpdateRequest request = ActorUpdateRequest.builder()
                .actorId(actorId)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        HttpEntity<ActorUpdateRequest> httpEntity = new HttpEntity<>(request);

        ResponseEntity<ActorFindByIdResponse> responseEntity = restTemplate.exchange(BASE_URL + "/update", HttpMethod.PUT, httpEntity, ActorFindByIdResponse.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ActorFindByIdResponse expectedResponse = ActorFindByIdResponse.builder()
                .actor(ActorDto.builder()
                        .actorId(actorId)
                        .firstName(firstName)
                        .lastName(lastName)
                        .build())
                .build();
        ActorFindByIdResponse body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        ActorDto dto = body.getActor();
        assertThat(dto).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedResponse.getActor());
        actorId = dto.getActorId();
    }

    @Order(4)
    @Test
    void findAll() {

        ResponseEntity<ActorFindAllResponse> responseEntity = restTemplate.getForEntity(BASE_URL + "/findAll", ActorFindAllResponse.class);
        ActorFindAllResponse body = responseEntity.getBody();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(body);
        assertThat(body.getActors()).hasSizeGreaterThanOrEqualTo(1);

    }

    @Order(5)
    @Test
    void delete() {
        ResponseEntity<ActorRemoveResponse> responseEntity = restTemplate.exchange(BASE_URL + "/delete?id={id}",
                HttpMethod.DELETE, null, ActorRemoveResponse.class, actorId);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
