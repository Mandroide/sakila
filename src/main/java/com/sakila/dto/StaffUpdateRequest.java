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
public class StaffUpdateRequest {
    @Min(1)
    @NotNull
    private Integer staffId;
    @Size(min = 2, max = 45)
    @NotBlank
    private String firstName;
    @Size(min = 2, max = 45)
    @NotBlank
    private String lastName;
    @Min(1)
    @NotNull
    private Integer addressId;
    private String picture;
    @Size(min = 2, max = 50)
    @NotBlank
    private String email;
    @Min(1)
    @NotNull
    private Integer storeId;
    @NotNull
    private Boolean active;
    @Size(min = 2, max = 16)
    @NotBlank
    private String username;
    @Size(min = 2, max = 40)
    @NotEmpty
    private String password;
}
