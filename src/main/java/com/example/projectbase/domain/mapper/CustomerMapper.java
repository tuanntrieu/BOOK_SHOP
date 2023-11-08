package com.example.projectbase.domain.mapper;

import com.example.projectbase.domain.dto.CustomerDto;
import com.example.projectbase.domain.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerDto customerDto);

}
