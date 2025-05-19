package com.sakila.entity;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
public class FilmActorPK implements Serializable {
    @Column(name = "film_id")
    private Integer filmId;
    @Column(name = "actor_id")
    private Integer actorId;
}
