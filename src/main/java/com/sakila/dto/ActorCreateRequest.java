package com.sakila.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ActorCreateRequest {
    @Size(min = 2, max = 45)
    @NotBlank
    private String firstName;
    @Size(min = 2, max = 45)
    @NotBlank
    private String lastName;
    @Builder.Default
    @JsonSetter(nulls = Nulls.SKIP)
    private Set<@NotNull Integer> filmIds = new HashSet<>();
}
