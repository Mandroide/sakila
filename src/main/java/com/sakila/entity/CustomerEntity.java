package com.sakila.entity;

import com.sakila.dto.CustomerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Table(name = "customer")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CustomerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "store_id")
    private Integer storeId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "address_id")
    private Integer addressId;
    @Column(name = "active", insertable = false)
    private Boolean active;
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;
    @Column(name = "last_update", insertable = false)
    private LocalDateTime lastUpdate;

    @PrePersist
    private void beforePersist() {
        setCreateDate(LocalDateTime.now());
        setLastUpdate(LocalDateTime.now());
    }

    @PreUpdate
    private void beforeUpdate() {
        setLastUpdate(LocalDateTime.now());
    }

    public CustomerDto toDto() {
        return CustomerDto.builder()
                .customerId(customerId)
                .storeId(storeId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .active(active)
                .createDate(createDate)
                .build();
    }
}
