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
public class FilmCategoryPK implements Serializable {
    @Column(name = "film_id")
    private Integer filmId;
    @Column(name = "category_id")
    private Integer categoryId;
}
