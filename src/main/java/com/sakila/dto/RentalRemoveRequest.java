package com.sakila.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RentalRemoveRequest {
    @Builder.Default
    @NotEmpty
    private Set<@NotNull @Min(value = 1) Integer> ids = new HashSet<>();
}
