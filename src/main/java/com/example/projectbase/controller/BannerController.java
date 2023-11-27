package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.BannerDto;
import com.example.projectbase.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @Operation(summary = "API get banners")
    @GetMapping(UrlConstant.Banner.GET_BANNERS)
    public ResponseEntity<?>getBanners(){
        return VsResponseUtil.success(bannerService.getBanners());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API update banner")
    @PutMapping(value=UrlConstant.Banner.UPDATE_BANNER,consumes = "multipart/form-data")
    public ResponseEntity<?> updateBanner(@PathVariable int bannerId, @Valid @ModelAttribute BannerDto bannerDto){
        return VsResponseUtil.success(bannerService.updateBanner(bannerId,bannerDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API delete banner")
    @DeleteMapping(UrlConstant.Banner.DELETE_BANNER)
    public ResponseEntity<?>deleteProductFromCart(@PathVariable int bannerId){
        return VsResponseUtil.success(bannerService.deleteBanner(bannerId));
    }
}
