package com.example.projectbase.domain.mapper;

import com.example.projectbase.domain.dto.CartDetailDto;
import com.example.projectbase.domain.entity.CartDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartDetailMapper {
    CartDetail toCartDetail(CartDetailDto cartDetailDto);
}
