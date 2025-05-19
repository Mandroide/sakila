package com.sakila.service;

import com.sakila.dto.*;
import com.sakila.entity.AddressEntity;
import com.sakila.entity.CityEntity;
import com.sakila.entity.CountryEntity;
import com.sakila.entity.CustomerEntity;
import com.sakila.repository.AddressRepository;
import com.sakila.repository.CityRepository;
import com.sakila.repository.CountryRepository;
import com.sakila.repository.CustomerRepository;
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
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public CustomerFindAllResponse findAll(CustomerFindAllRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<CustomerDto> page = customerRepository.findAll(pageRequest).map(this::fillData);
        List<CustomerDto> content = page.getContent();

        return CustomerFindAllResponse.builder()
                .page(request.getPageNumber())
                .totalPages(page.getTotalPages())
                .totalResults(page.getNumberOfElements())
                .customers(content)
                .build();
    }

    private CustomerDto fillData(CustomerEntity entity) {
        AddressEntity addressEntity = addressRepository.findById(entity.getAddressId()).orElseThrow();
        CityEntity cityEntity = cityRepository.findById(addressEntity.getCityId()).orElseThrow();
        String country = countryRepository.findById(cityEntity.getCountryId()).map(CountryEntity::getCountry).orElseThrow();
        CustomerDto dto = entity.toDto();
        AddressDto addressDto = addressEntity.toDto();
        addressDto.setCity(cityEntity.getCity());
        addressDto.setCountry(country);
        dto.setAddressDto(addressDto);
        return dto;
    }

    public CustomerFindByIdResponse findById(Integer id) {
        CustomerDto dto = fillData(customerRepository.findById(id).orElseThrow());
        return CustomerFindByIdResponse.builder()
                .customerDto(dto)
                .build();
    }


    @Transactional
    public CustomerFindByIdResponse create(CustomerCreateRequest request) {
        CustomerEntity entity = CustomerEntity.builder()
                .storeId(request.getStoreId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .addressId(request.getAddressId())
                .build();
        CustomerDto dto = fillData(customerRepository.save(entity));
        return CustomerFindByIdResponse.builder()
                .customerDto(dto)
                .build();
    }

    @Transactional
    public CustomerFindByIdResponse update(CustomerUpdateRequest request) {
        CustomerEntity entity = customerRepository.findById(request.getCustomerId()).orElseThrow();
        CustomerDto dto = fillData(customerRepository.save(entity));
        return CustomerFindByIdResponse.builder()
                .customerDto(dto)
                .build();
    }

    @Transactional
    public CustomerRemoveResponse delete(Integer id) {
        customerRepository.deleteById(id);
        return CustomerRemoveResponse.builder()
                .message("Customer with id " + id + " has been deleted")
                .build();
    }
}
