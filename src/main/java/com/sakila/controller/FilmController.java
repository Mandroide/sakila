package com.sakila.controller;

import com.sakila.dto.*;
import com.sakila.service.FilmService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping(("/findAll"))
    public ResponseEntity<FilmFindAllResponse> findAllFilms(@Valid FilmFindAllRequest request) {
        return ResponseEntity.ok(filmService.findAll(request));
    }

    @GetMapping("/findById")
    public ResponseEntity<FilmFindByIdResponse> findFilmById(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.ok(filmService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<FilmFindByIdResponse> createFilm(@Valid @RequestBody FilmCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(filmService.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<FilmFindByIdResponse> updateFilm(@Valid @RequestBody FilmUpdateRequest request) {
        return ResponseEntity.ok(filmService.update(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<FilmRemoveResponse> deleteFilm(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(filmService.delete(id));
    }
}
