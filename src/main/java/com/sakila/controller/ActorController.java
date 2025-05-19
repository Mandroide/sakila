package com.sakila.controller;

import com.sakila.dto.*;
import com.sakila.service.ActorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/actors")
@RequiredArgsConstructor
public class ActorController {
    private final ActorService actorService;

    @GetMapping(("/findAll"))
    public ResponseEntity<ActorFindAllResponse> findAll(@Valid ActorFindAllRequest request) {
        return ResponseEntity.ok(actorService.findAll(request));
    }

    @GetMapping("/findById")
    public ResponseEntity<ActorFindByIdResponse> findById(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.ok(actorService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ActorFindByIdResponse> create(@Valid @RequestBody ActorCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(actorService.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<ActorFindByIdResponse> update(@Valid @RequestBody ActorUpdateRequest request) {
        return ResponseEntity.ok(actorService.update(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ActorRemoveResponse> delete(@NotNull @Min(value = 1) Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(actorService.delete(id));
    }
}
