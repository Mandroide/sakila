package com.sakila.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PaymentDto {
    private Integer paymentId;
    private Integer customerId;
    private Integer staffId;
    private Integer rentalId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
}
