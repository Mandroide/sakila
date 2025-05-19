package com.sakila.service;

import com.sakila.dto.*;
import com.sakila.entity.RentalEntity;
import com.sakila.repository.RentalRepository;
import com.sakila.util.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalFindAllResponse findAll(RentalFindAllRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<RentalDto> page = rentalRepository.findAll(pageRequest).map(RentalEntity::toDto);
        List<RentalDto> content = page.getContent();

        return RentalFindAllResponse.builder()
                .page(request.getPageNumber())
                .totalPages(page.getTotalPages())
                .totalResults(page.getNumberOfElements())
                .rentalDtos(content)
                .build();
    }

    public RentalFindByIdResponse findById(Integer id) {
        return RentalFindByIdResponse.builder()
                .rentalDto(rentalRepository.findById(id).map(RentalEntity::toDto).orElseThrow())
                .build();
    }


    @Transactional
    public RentalFindByIdResponse create(RentalCreateRequest request) {
        boolean isRentalFound = rentalRepository.findByRentalDateAndInventoryIdAndCustomerId(request.getRentalDate(), request.getInventoryId(),
                request.getCustomerId()).isPresent();
        if (isRentalFound) {
            throw new BadRequestException("Rental already exists");
        }
        RentalEntity entity = RentalEntity.builder()
                .rentalDate(request.getRentalDate())
                .inventoryId(request.getInventoryId())
                .customerId(request.getCustomerId())
                .returnDate(request.getReturnDate())
                .staffId(request.getStaffId())
                .build();
        RentalDto rentalDto = rentalRepository.save(entity).toDto();
        return RentalFindByIdResponse.builder()
                .rentalDto(rentalDto)
                .build();
    }

    @Transactional
    public RentalRemoveResponse delete(RentalRemoveRequest request) {
        Set<Integer> ids = request.getIds();
        rentalRepository.deleteAllById(ids);
        String deletedIds = String.join(",", ids.stream().map(String::valueOf).toArray(String[]::new));
        return RentalRemoveResponse.builder()
                .message("Rentals with ids " + deletedIds + " have been deleted")
                .build();
    }
}

