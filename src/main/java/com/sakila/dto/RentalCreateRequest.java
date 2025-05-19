package com.sakila.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RentalCreateRequest {
    @NotNull
    private LocalDateTime rentalDate;
    @Min(1)
    @NotNull
    private Integer inventoryId;
    @Min(1)
    @NotNull
    private Integer customerId;
    private LocalDateTime returnDate;
    @Min(1)
    @NotNull
    private Integer staffId;
}
