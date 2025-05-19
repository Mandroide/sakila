package com.sakila.repository;

import com.sakila.entity.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Integer> {
    Optional<RentalEntity> findByRentalDateAndInventoryIdAndCustomerId(LocalDateTime rentalDate, Integer inventoryId, Integer customerId);
}
