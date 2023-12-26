package com.example.projectbase.service;

import com.example.projectbase.domain.dto.BannerDto;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.entity.Banner;

import java.util.List;

public interface BannerService {
    PaginationResponseDto getBanners(PaginationFullRequestDto requestDto);

    CommonResponseDto updateBanner(int bannerId, BannerDto bannerDto);

    CommonResponseDto deleteBanner(int bannerId);

    Banner createBanner(BannerDto bannerDto);
}
