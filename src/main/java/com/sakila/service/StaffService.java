package com.sakila.service;

import com.sakila.dto.*;
import com.sakila.entity.AddressEntity;
import com.sakila.entity.CityEntity;
import com.sakila.entity.CountryEntity;
import com.sakila.entity.StaffEntity;
import com.sakila.repository.*;
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
public class StaffService {
    private final StaffRepository staffRepository;
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public StaffFindAllResponse findAll(StaffFindAllRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<StaffDto> page = staffRepository.findAll(pageRequest).map(this::fillData);
        List<StaffDto> content = page.getContent();

        return StaffFindAllResponse.builder()
                .page(request.getPageNumber())
                .totalPages(page.getTotalPages())
                .totalResults(page.getNumberOfElements())
                .staffDtos(content)
                .build();
    }

    private StaffDto fillData(StaffEntity entity) {
        AddressEntity addressEntity = addressRepository.findById(entity.getAddressId()).orElseThrow();
        CityEntity cityEntity = cityRepository.findById(addressEntity.getCityId()).orElseThrow();
        String country = countryRepository.findById(cityEntity.getCountryId()).map(CountryEntity::getCountry).orElseThrow();
        StaffDto dto = entity.toDto();
        AddressDto addressDto = addressEntity.toDto();
        addressDto.setCity(cityEntity.getCity());
        addressDto.setCountry(country);
        dto.setAddressDto(addressDto);
        return dto;
    }

    public StaffFindByIdResponse findById(Integer id) {
        StaffDto dto = fillData(staffRepository.findById(id).orElseThrow());
        return StaffFindByIdResponse.builder()
                .staffDto(dto)
                .build();
    }

    @Transactional
    public StaffFindByIdResponse create(StaffCreateRequest request) {
        StaffEntity entity = StaffEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .addressId(request.getAddressId())
                .picture(request.getPicture())
                .email(request.getEmail())
                .storeId(request.getStoreId())
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
        StaffDto dto = fillData(staffRepository.save(entity));
        return StaffFindByIdResponse.builder()
                .staffDto(dto)
                .build();
    }

    @Transactional
    public StaffFindByIdResponse update(StaffUpdateRequest request) {
        StaffEntity entity = staffRepository.findById(request.getStaffId()).orElseThrow();
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setAddressId(request.getAddressId());
        entity.setPicture(request.getPicture());
        entity.setEmail(request.getEmail());
        entity.setStoreId(request.getStoreId());
        entity.setActive(request.getActive());
        entity.setUsername(request.getUsername());
        entity.setPassword(request.getPassword());
        StaffDto dto = fillData(staffRepository.save(entity));
        return StaffFindByIdResponse.builder()
                .staffDto(dto)
                .build();
    }

    @Transactional
    public StaffRemoveResponse delete(Integer id) {
        staffRepository.deleteById(id);
        return StaffRemoveResponse.builder()
                .message("Staff with id " + id + " has been deleted")
                .build();
    }
}
