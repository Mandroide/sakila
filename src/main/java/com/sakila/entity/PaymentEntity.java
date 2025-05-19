package com.sakila.entity;

import com.sakila.dto.PaymentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Table(name = "payment")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PaymentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "payment_id")
    private Integer paymentId;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "staff_id")
    private Integer staffId;
    @Column(name = "rental_id")
    private Integer rentalId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
    @Column(name = "last_update", insertable = false)
    private LocalDateTime lastUpdate;

    @PrePersist
    private void beforePersist() {
        setPaymentDate(LocalDateTime.now());
    }

    @PreUpdate
    private void beforeUpdate() {
        setLastUpdate(LocalDateTime.now());
    }

    public PaymentDto toDto() {
        return PaymentDto.builder()
                .paymentId(paymentId)
                .customerId(customerId)
                .staffId(staffId)
                .rentalId(rentalId)
                .amount(amount)
                .paymentDate(paymentDate)
                .build();
    }
}
