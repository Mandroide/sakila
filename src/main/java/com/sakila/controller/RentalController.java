package com.sakila.controller;

import com.sakila.dto.*;
import com.sakila.service.RentalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @GetMapping(("/findAll"))
    public ResponseEntity<RentalFindAllResponse> findAll(@Valid RentalFindAllRequest request) {
        return ResponseEntity.ok(rentalService.findAll(request));
    }

    @GetMapping("/findById")
    public ResponseEntity<RentalFindByIdResponse> findById(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.ok(rentalService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<RentalFindByIdResponse> create(@Valid @RequestBody RentalCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.create(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<RentalRemoveResponse> delete(@Valid @RequestBody RentalRemoveRequest request) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rentalService.delete(request));
    }
}
