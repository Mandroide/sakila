package com.sakila.entity;

import com.sakila.dto.ActorDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Table(name = "actor")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ActorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "actor_id")
    private Integer actorId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "last_update", insertable = false)
    private LocalDateTime lastUpdate;

    @PreUpdate
    private void beforeUpdate() {
        setLastUpdate(LocalDateTime.now());
    }

    public ActorDto toDto() {
        return ActorDto.builder()
                .actorId(actorId)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
