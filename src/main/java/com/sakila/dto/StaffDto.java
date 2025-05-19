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
public class StaffDto {
    private Integer staffId;
    private String firstName;
    private String lastName;
    private AddressDto addressDto;
    private String picture;
    private String email;
    private Integer storeId;
    private Boolean active;
    private String username;
    private String password;
}
