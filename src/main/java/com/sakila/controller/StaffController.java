package com.sakila.controller;

import com.sakila.dto.*;
import com.sakila.service.StaffService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/staff")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;

    @GetMapping(("/findAll"))
    public ResponseEntity<StaffFindAllResponse> findAll(@Valid StaffFindAllRequest request) {
        return ResponseEntity.ok(staffService.findAll(request));
    }

    @GetMapping("/findById")
    public ResponseEntity<StaffFindByIdResponse> findById(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.ok(staffService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<StaffFindByIdResponse> create(@Valid @RequestBody StaffCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(staffService.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<StaffFindByIdResponse> update(@Valid @RequestBody StaffUpdateRequest request) {
        return ResponseEntity.ok(staffService.update(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<StaffRemoveResponse> delete(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(staffService.delete(id));
    }
}
