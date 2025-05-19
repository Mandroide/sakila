package com.sakila.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StaffFindAllRequest {
    @Builder.Default
    @JsonSetter(nulls = Nulls.SKIP)
    @NotNull
    @Min(0)
    private Integer pageNumber = 0;
    @Builder.Default
    @JsonSetter(nulls = Nulls.SKIP)
    @Min(10)
    @Max(100)
    @NotNull
    private Integer pageSize = 10;
}
