package com.sakila.service;

import com.sakila.dto.*;
import com.sakila.entity.CityEntity;
import com.sakila.entity.CountryEntity;
import com.sakila.repository.CityRepository;
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
public class CityService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public CityFindAllResponse findAll(CityFindAllRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<CityDto> page = cityRepository.findAll(pageRequest).map(this::fillData);
        List<CityDto> content = page.getContent();

        return CityFindAllResponse.builder()
                .page(request.getPageNumber())
                .totalPages(page.getTotalPages())
                .totalResults(page.getNumberOfElements())
                .cities(content)
                .build();
    }

    private CityDto fillData(CityEntity entity) {
        String country = countryRepository.findById(entity.getCountryId()).map(CountryEntity::getCountry).orElseThrow();
        CityDto dto = entity.toDto();
        dto.setCountry(country);
        return dto;
    }

    public CityFindByIdResponse findById(Integer id) {
        CityDto dto = fillData(cityRepository.findById(id).orElseThrow());
        return CityFindByIdResponse.builder()
                .cityDto(dto)
                .build();
    }


    @Transactional
    public CityFindByIdResponse create(CityCreateRequest request) {
        CityEntity entity = CityEntity.builder()
                .city(request.getCity())
                .countryId(request.getCountryId())
                .build();
        CityDto dto = fillData(cityRepository.save(entity));
        return CityFindByIdResponse.builder()
                .cityDto(dto)
                .build();
    }

    @Transactional
    public CityFindByIdResponse update(CityUpdateRequest request) {
        CityEntity entity = cityRepository.findById(request.getCityId()).orElseThrow();
        entity.setCity(request.getCity());
        CityDto dto = fillData(cityRepository.save(entity));
        return CityFindByIdResponse.builder()
                .cityDto(dto)
                .build();
    }

    @Transactional
    public CityRemoveResponse delete(Integer id) {
        cityRepository.deleteById(id);
        return CityRemoveResponse.builder()
                .message("City with id " + id + " has been deleted")
                .build();
    }
}
