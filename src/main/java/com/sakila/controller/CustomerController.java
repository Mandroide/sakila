package com.sakila.controller;

import com.sakila.dto.*;
import com.sakila.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService addressService;

    @GetMapping(("/findAll"))
    public ResponseEntity<CustomerFindAllResponse> findAll(@Valid CustomerFindAllRequest request) {
        return ResponseEntity.ok(addressService.findAll(request));
    }

    @GetMapping("/findById")
    public ResponseEntity<CustomerFindByIdResponse> findById(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerFindByIdResponse> create(@Valid @RequestBody CustomerCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerFindByIdResponse> update(@Valid @RequestBody CustomerUpdateRequest request) {
        return ResponseEntity.ok(addressService.update(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CustomerRemoveResponse> delete(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(addressService.delete(id));
    }
}
