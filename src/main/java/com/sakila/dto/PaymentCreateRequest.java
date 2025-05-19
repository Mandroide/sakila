package com.sakila.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PaymentCreateRequest {
    @Min(1)
    @NotNull
    private Integer customerId;
    @Min(1)
    @NotNull
    private Integer staffId;
    @Min(1)
    @NotNull
    private Integer rentalId;
    @Digits(integer = 5, fraction = 2)
    private BigDecimal amount;
}
