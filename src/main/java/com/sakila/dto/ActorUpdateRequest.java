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
public class ActorUpdateRequest {
    @Min(1)
    @NotNull
    private Integer actorId;
    @Size(min = 2, max = 45)
    @NotBlank
    private String firstName;
    @Size(min = 2, max = 45)
    @NotBlank
    private String lastName;
}
