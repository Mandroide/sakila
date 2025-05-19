package com.sakila.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@IdClass(FilmCategoryPK.class)
@Table(name = "film_category")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FilmCategoryEntity {
    @EqualsAndHashCode.Include
    @Id
    private Integer filmId;
    @Id
    @EqualsAndHashCode.Include
    private Integer categoryId;
    @Column(name = "last_update", insertable = false)
    private LocalDateTime lastUpdate;

    @PreUpdate
    private void beforeUpdate() {
        setLastUpdate(LocalDateTime.now());
    }
}
