package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.entity.Banner;
import com.example.projectbase.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API get banner")
    @GetMapping(UrlConstant.Banner.GET_BANNER)
    public ResponseEntity<?> getBanner(@PathVariable int bannerId) {
        return VsResponseUtil.success(bannerService.getBanner(bannerId));
    }

    @Operation(summary = "API get banners")
    @GetMapping(UrlConstant.Banner.GET_BANNERS)
    public ResponseEntity<?> getBanners(@ParameterObject @Valid PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(bannerService.getBanners(requestDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API create banner")
    @PostMapping(value = UrlConstant.Banner.CREATE_BANNER)
    public ResponseEntity<?> createBanner(@Valid @RequestBody Banner banner) {
        return VsResponseUtil.success(bannerService.createBanner(banner));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API update banner")
    @PutMapping(value = UrlConstant.Banner.UPDATE_BANNER)
    public ResponseEntity<?> updateBanner(@PathVariable int bannerId, @Valid @RequestBody Banner banner) {
        return VsResponseUtil.success(bannerService.updateBanner(bannerId, banner));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API delete banner")
    @DeleteMapping(UrlConstant.Banner.DELETE_BANNER)
    public ResponseEntity<?> deleteProductFromCart(@PathVariable int bannerId) {
        return VsResponseUtil.success(bannerService.deleteBanner(bannerId));
    }
}
