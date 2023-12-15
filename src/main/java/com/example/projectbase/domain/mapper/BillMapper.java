package com.example.projectbase.domain.mapper;

import com.example.projectbase.domain.dto.BillDto;
import com.example.projectbase.domain.entity.Bill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillMapper {

    Bill toBill(BillDto billDto);
}
