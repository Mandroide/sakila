package com.sakila.service;

import com.sakila.dto.*;
import com.sakila.entity.AddressEntity;
import com.sakila.entity.CityEntity;
import com.sakila.entity.CountryEntity;
import com.sakila.repository.AddressRepository;
import com.sakila.repository.CityRepository;
import com.sakila.repository.CountryRepository;
import com.sakila.util.GeometryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.io.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public AddressFindAllResponse findAll(AddressFindAllRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<AddressDto> page = addressRepository.findAll(pageRequest).map(this::fillData);
        List<AddressDto> content = page.getContent();

        return AddressFindAllResponse.builder()
                .page(request.getPageNumber())
                .totalPages(page.getTotalPages())
                .totalResults(page.getNumberOfElements())
                .addresses(content)
                .build();
    }

    private AddressDto fillData(AddressEntity entity) {
        CityEntity cityEntity = cityRepository.findById(entity.getCityId()).orElseThrow();
        String country = countryRepository.findById(cityEntity.getCountryId()).map(CountryEntity::getCountry).orElseThrow();
        AddressDto dto = entity.toDto();
        dto.setCity(cityEntity.getCity());
        dto.setCountry(country);
        return dto;
    }

    public AddressFindByIdResponse findById(Integer id) {
        AddressDto dto = fillData(addressRepository.findById(id).orElseThrow());
        return AddressFindByIdResponse.builder()
                .address(dto)
                .build();
    }


    @Transactional
    public AddressFindByIdResponse create(AddressCreateRequest request) throws ParseException {
        AddressEntity entity = AddressEntity.builder()
                .address(request.getAddress())
                .address2(request.getAddress2())
                .district(request.getDistrict())
                .cityId(request.getCityId())
                .postalCode(request.getPostalCode())
                .phone(request.getPhone())
                .location(GeometryUtil.wktToPoint(request.getLocation()))
                .build();
        AddressDto dto = fillData(addressRepository.save(entity));
        return AddressFindByIdResponse.builder()
                .address(dto)
                .build();
    }

    @Transactional
    public AddressFindByIdResponse update(AddressUpdateRequest request) {
        AddressEntity entity = addressRepository.findById(request.getAddressId()).orElseThrow();
        AddressDto dto = fillData(addressRepository.save(entity));
        return AddressFindByIdResponse.builder()
                .address(dto)
                .build();
    }

    @Transactional
    public AddressRemoveResponse delete(Integer id) {
        addressRepository.deleteById(id);
        return AddressRemoveResponse.builder()
                .message("Address with id " + id + " has been deleted")
                .build();
    }
}
