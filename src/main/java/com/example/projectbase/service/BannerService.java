package com.example.projectbase.service;

import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.entity.Banner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerService {
    Banner getBanner(int bannerId);


    PaginationResponseDto<Banner> getBanners(PaginationFullRequestDto requestDto);

    CommonResponseDto updateBanner(int bannerId, Banner bannerDto);

    CommonResponseDto deleteBanner(int bannerId);

    Banner createBanner(Banner bannerDto);
}
