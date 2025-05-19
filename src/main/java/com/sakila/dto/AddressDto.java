package com.sakila.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AddressDto {
    private Integer addressId;
    private String address;
    private String address2;
    private String district;
    private String city;
    private String country;
    private String postalCode;
    private String phone;
    private String location;
}
