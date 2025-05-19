package com.sakila.service;

import com.sakila.dto.*;
import com.sakila.entity.PaymentEntity;
import com.sakila.repository.PaymentRepository;
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
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentFindAllResponse findAll(PaymentFindAllRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<PaymentDto> page = paymentRepository.findAll(pageRequest).map(PaymentEntity::toDto);
        List<PaymentDto> content = page.getContent();

        return PaymentFindAllResponse.builder()
                .page(request.getPageNumber())
                .totalPages(page.getTotalPages())
                .totalResults(page.getNumberOfElements())
                .payments(content)
                .build();
    }

    public PaymentFindByIdResponse findById(Integer id) {
        return PaymentFindByIdResponse.builder()
                .paymentDto(paymentRepository.findById(id).map(PaymentEntity::toDto).orElseThrow())
                .build();
    }


    @Transactional
    public PaymentFindByIdResponse create(PaymentCreateRequest request) {
        PaymentEntity entity = PaymentEntity.builder()
                .customerId(request.getCustomerId())
                .staffId(request.getStaffId())
                .rentalId(request.getRentalId())
                .amount(request.getAmount())
                .build();
        PaymentDto paymentDto = paymentRepository.save(entity).toDto();
        return PaymentFindByIdResponse.builder()
                .paymentDto(paymentDto)
                .build();
    }

    @Transactional
    public PaymentRemoveResponse delete(PaymentRemoveRequest request) {
        Set<Integer> ids = request.getIds();
        paymentRepository.deleteAllById(ids);
        String deletedIds = String.join(",", ids.stream().map(String::valueOf).toArray(String[]::new));
        return PaymentRemoveResponse.builder()
                .message("Payments with ids " + deletedIds + " have been deleted")
                .build();
    }
}

