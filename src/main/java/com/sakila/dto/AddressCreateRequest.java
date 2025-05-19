package com.sakila.dto;

import jakarta.validation.constraints.*;
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
public class AddressCreateRequest {
    @Size(min = 2, max = 50)
    @NotBlank
    private String address;
    @Size(min = 2, max = 50)
    @NotEmpty
    private String address2;
    @Size(min = 2, max = 20)
    @NotBlank
    private String district;
    @Min(1)
    @NotNull
    private Integer cityId;
    @Size(min = 2, max = 10)
    @NotEmpty
    private String postalCode;
    @Pattern(regexp = "^\\d{15}$")
    @NotNull
    private String phone;
    @NotNull
    private String location;
}
