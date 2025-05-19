package com.sakila.controller;

import com.sakila.dto.*;
import com.sakila.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping(("/findAll"))
    public ResponseEntity<PaymentFindAllResponse> findAll(@Valid PaymentFindAllRequest request) {
        return ResponseEntity.ok(paymentService.findAll(request));
    }

    @GetMapping("/findById")
    public ResponseEntity<PaymentFindByIdResponse> findById(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.ok(paymentService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentFindByIdResponse> create(@Valid @RequestBody PaymentCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.create(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<PaymentRemoveResponse> delete(@Valid @RequestBody PaymentRemoveRequest request) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(paymentService.delete(request));
    }
}
