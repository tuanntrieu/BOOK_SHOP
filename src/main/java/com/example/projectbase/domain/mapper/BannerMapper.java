package com.example.projectbase.domain.mapper;

import com.example.projectbase.domain.dto.BannerDto;
import com.example.projectbase.domain.entity.Banner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BannerMapper {

    Banner toBanner(BannerDto bannerDto);

}
