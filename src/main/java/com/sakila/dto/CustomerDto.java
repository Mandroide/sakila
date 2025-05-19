package com.sakila.dto;

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
public class CustomerDto {
    private Integer customerId;
    private Integer storeId;
    private String firstName;
    private String lastName;
    private String email;
    private AddressDto addressDto;
    private Boolean active;
    private LocalDateTime createDate;
}
