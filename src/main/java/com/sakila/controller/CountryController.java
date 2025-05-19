package com.sakila.controller;

import com.sakila.dto.*;
import com.sakila.service.CountryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @GetMapping(("/findAll"))
    public ResponseEntity<CountryFindAllResponse> findAll(@Valid CountryFindAllRequest request) {
        return ResponseEntity.ok(countryService.findAll(request));
    }

    @GetMapping("/findById")
    public ResponseEntity<CountryFindByIdResponse> findById(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.ok(countryService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CountryFindByIdResponse> create(@Valid @RequestBody CountryCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(countryService.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<CountryFindByIdResponse> update(@Valid @RequestBody CountryUpdateRequest request) {
        return ResponseEntity.ok(countryService.update(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CountryRemoveResponse> delete(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(countryService.delete(id));
    }
}
