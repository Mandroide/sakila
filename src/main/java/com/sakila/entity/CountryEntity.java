package com.sakila.entity;

import com.sakila.dto.CountryDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Table(name = "country")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CountryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "country_id")
    private Integer countryId;
    @Column(name = "country")
    private String country;
    @Column(name = "last_update", insertable = false)
    private LocalDateTime lastUpdate;

    @PreUpdate
    private void beforeUpdate() {
        setLastUpdate(LocalDateTime.now());
    }

    public CountryDto toDto() {
        return CountryDto.builder()
                .countryId(countryId)
                .country(country)
                .build();
    }
}
