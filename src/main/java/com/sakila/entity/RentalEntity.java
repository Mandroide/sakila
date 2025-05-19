package com.sakila.entity;

import com.sakila.dto.RentalDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Table(name = "rental")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RentalEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "rental_id")
    private Integer rentalId;
    @Column(name = "rental_date")
    private LocalDateTime rentalDate;
    @Column(name = "inventory_id")
    private Integer inventoryId;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "return_date")
    private LocalDateTime returnDate;
    @Column(name = "staff_id")
    private Integer staffId;
    @Column(name = "last_update", insertable = false)
    private LocalDateTime lastUpdate;

    @PreUpdate
    private void beforeUpdate() {
        setLastUpdate(LocalDateTime.now());
    }

    public RentalDto toDto() {
        return RentalDto.builder()
                .rentalId(rentalId)
                .rentalDate(rentalDate)
                .inventoryId(inventoryId)
                .customerId(customerId)
                .returnDate(returnDate)
                .staffId(staffId)
                .build();
    }
}
