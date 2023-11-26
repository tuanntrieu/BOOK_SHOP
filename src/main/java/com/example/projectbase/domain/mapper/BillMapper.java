package com.example.projectbase.domain.mapper;

import com.example.projectbase.domain.dto.BillDto;
import com.example.projectbase.domain.entity.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BillMapper {

    Bill toBill(BillDto billDto);
}
