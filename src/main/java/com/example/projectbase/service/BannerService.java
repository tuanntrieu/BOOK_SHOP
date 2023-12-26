package com.example.projectbase.service;

import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.entity.Banner;

import java.util.List;

public interface BannerService {
    Banner getBanner(int bannerId);

    List<Banner> getBanners();

    CommonResponseDto updateBanner(int bannerId, Banner bannerDto);

    CommonResponseDto deleteBanner(int bannerId);

    Banner createBanner(Banner bannerDto);
}
