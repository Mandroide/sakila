package com.sakila.service;

import com.sakila.dto.*;
import com.sakila.entity.CountryEntity;
import com.sakila.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryFindAllResponse findAll(CountryFindAllRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<CountryDto> page = countryRepository.findAll(pageRequest).map(CountryEntity::toDto);
        List<CountryDto> content = page.getContent();

        return CountryFindAllResponse.builder()
                .page(request.getPageNumber())
                .totalPages(page.getTotalPages())
                .totalResults(page.getNumberOfElements())
                .countries(content)
                .build();
    }

    public CountryFindByIdResponse findById(Integer id) {
        return CountryFindByIdResponse.builder()
                .countryDto(countryRepository.findById(id).map(CountryEntity::toDto).orElseThrow())
                .build();
    }


    @Transactional
    public CountryFindByIdResponse create(CountryCreateRequest request) {
        CountryEntity entity = CountryEntity.builder()
                .country(request.getCountry())
                .build();
        CountryDto dto = countryRepository.save(entity).toDto();
        return CountryFindByIdResponse.builder()
                .countryDto(dto)
                .build();
    }

    @Transactional
    public CountryFindByIdResponse update(CountryUpdateRequest request) {
        CountryEntity entity = countryRepository.findById(request.getCountryId()).orElseThrow();
        entity.setCountry(request.getCountry());
        CountryDto dto = countryRepository.save(entity).toDto();
        return CountryFindByIdResponse.builder()
                .countryDto(dto)
                .build();
    }

    @Transactional
    public CountryRemoveResponse delete(Integer id) {
        countryRepository.deleteById(id);
        return CountryRemoveResponse.builder()
                .message("Country with id " + id + " has been deleted")
                .build();
    }
}
