package com.example.projectbase.service;

import com.example.projectbase.domain.dto.BannerDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.entity.Banner;

import java.util.List;

public interface BannerService {
    List<Banner> getBanners();

    CommonResponseDto updateBanner(int bannerId, BannerDto bannerDto);

    CommonResponseDto deleteBanner(int bannerId);
}
