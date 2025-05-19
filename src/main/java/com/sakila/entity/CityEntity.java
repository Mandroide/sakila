package com.sakila.entity;

import com.sakila.dto.CityDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Table(name = "city")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CityEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "city_id")
    private Integer cityId;
    @Column(name = "city")
    private String city;
    @Column(name = "country_id")
    private Integer countryId;
    @Column(name = "last_update", insertable = false)
    private LocalDateTime lastUpdate;

    @PreUpdate
    private void beforeUpdate() {
        setLastUpdate(LocalDateTime.now());
    }

    public CityDto toDto() {
        return CityDto.builder()
                .cityId(cityId)
                .city(city)
                .build();
    }
}
