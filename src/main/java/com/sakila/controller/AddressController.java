package com.sakila.controller;

import com.sakila.dto.*;
import com.sakila.service.AddressService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping(("/findAll"))
    public ResponseEntity<AddressFindAllResponse> findAll(@Valid AddressFindAllRequest request) {
        return ResponseEntity.ok(addressService.findAll(request));
    }

    @GetMapping("/findById")
    public ResponseEntity<AddressFindByIdResponse> findById(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<AddressFindByIdResponse> create(@Valid @RequestBody AddressCreateRequest request) throws ParseException {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<AddressFindByIdResponse> update(@Valid @RequestBody AddressUpdateRequest request) {
        return ResponseEntity.ok(addressService.update(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<AddressRemoveResponse> delete(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(addressService.delete(id));
    }
}
