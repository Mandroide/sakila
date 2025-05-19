package com.sakila.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@IdClass(FilmActorPK.class)
@Table(name = "film_actor")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FilmActorEntity {
    @EqualsAndHashCode.Include
    @Id
    private Integer filmId;
    @Id
    @EqualsAndHashCode.Include
    private Integer actorId;
    @Column(name = "last_update", insertable = false)
    private LocalDateTime lastUpdate;

    @PreUpdate
    private void beforeUpdate() {
        setLastUpdate(LocalDateTime.now());
    }
}
