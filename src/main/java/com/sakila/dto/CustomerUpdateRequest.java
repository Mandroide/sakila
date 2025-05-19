package com.sakila.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CustomerUpdateRequest {
    @Min(1)
    @NotNull
    private Integer customerId;
    @Min(1)
    @NotNull
    private Integer storeId;
    @Size(min = 2, max = 45)
    @NotBlank
    private String firstName;
    @Size(min = 2, max = 45)
    @NotBlank
    private String lastName;
    @Size(min = 2, max = 50)
    @NotBlank
    private String email;
    @Min(1)
    @NotNull
    private Integer addressId;
    @NotNull
    private Boolean active;
}
