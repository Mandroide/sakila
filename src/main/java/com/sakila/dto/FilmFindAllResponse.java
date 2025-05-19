package com.sakila.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FilmFindAllResponse {
    private Integer page;
    private Integer totalPages;
    private Integer totalResults;
    private List<FilmDto> films;
}
