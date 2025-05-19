package com.sakila.entity;

import com.sakila.dto.StaffDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Table(name = "staff")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StaffEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "staff_id")
    private Integer staffId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "address_id")
    private Integer addressId;
    @Column(name = "picture")
    private String picture;
    @Column(name = "email")
    private String email;
    @Column(name = "store_id")
    private Integer storeId;
    @Column(name = "active", insertable = false)
    private Boolean active;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "last_update", insertable = false)
    private LocalDateTime lastUpdate;

    @PreUpdate
    private void beforeUpdate() {
        setLastUpdate(LocalDateTime.now());
    }

    public StaffDto toDto() {
        return StaffDto.builder()
                .staffId(staffId)
                .firstName(firstName)
                .lastName(lastName)
                .picture(picture)
                .email(email)
                .storeId(storeId)
                .active(active)
                .username(username)
                .password(password)
                .build();
    }

}
