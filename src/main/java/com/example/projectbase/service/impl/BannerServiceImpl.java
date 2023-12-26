package com.example.projectbase.service.impl;

import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.constant.SuccessMessage;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.entity.Banner;
import com.example.projectbase.exception.NotFoundException;
import com.example.projectbase.repository.BannerRepository;
import com.example.projectbase.service.BannerService;
import com.example.projectbase.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;
    private final UploadFileUtil uploadFileUtil;

    @Override
    public Banner getBanner(int bannerId) {
        return bannerRepository.findById(bannerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Banner.ERR_NOT_FOUND_ID, new String[]{String.valueOf(bannerId)}));
    }

    @Override
    public List<Banner> getBanners() {
        return bannerRepository.findAll();
    }

    @Override
    public CommonResponseDto updateBanner(int bannerId, Banner bannerDto) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Banner.ERR_NOT_FOUND_ID, new String[]{String.valueOf(bannerId)}));
        if (banner.getImage() != null) {
            uploadFileUtil.destroyFileWithUrl(banner.getImage());
        }
        bannerRepository.save(banner);
        return new CommonResponseDto(SuccessMessage.UPDATE);
    }

    @Override
    public CommonResponseDto deleteBanner(int bannerId) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Banner.ERR_NOT_FOUND_ID, new String[]{String.valueOf(bannerId)}));
        bannerRepository.delete(banner);
        return new CommonResponseDto(SuccessMessage.DELETE);
    }

    @Override
    public Banner createBanner(Banner bannerDto) {
        Banner banner;
        if (bannerDto.getId() == -1) {
            banner = bannerDto;
        } else {
            banner = bannerRepository.findById(bannerDto.getId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Banner.ERR_NOT_FOUND_ID, new String[]{String.valueOf(bannerDto.getId())}));
            if (banner.getImage() != null) {
                uploadFileUtil.destroyFileWithUrl(banner.getImage());
            }
            banner.setImage(bannerDto.getImage());
            banner.setUrl(bannerDto.getUrl());
        }
        return bannerRepository.save(banner);
    }
}
