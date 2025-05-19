package com.sakila.controller;

import com.sakila.dto.*;
import com.sakila.service.CityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cities")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping(("/findAll"))
    public ResponseEntity<CityFindAllResponse> findAll(@Valid CityFindAllRequest request) {
        return ResponseEntity.ok(cityService.findAll(request));
    }

    @GetMapping("/findById")
    public ResponseEntity<CityFindByIdResponse> findById(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.ok(cityService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CityFindByIdResponse> create(@Valid @RequestBody CityCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<CityFindByIdResponse> update(@Valid @RequestBody CityUpdateRequest request) {
        return ResponseEntity.ok(cityService.update(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CityRemoveResponse> delete(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cityService.delete(id));
    }
}
