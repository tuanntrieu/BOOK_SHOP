package com.example.projectbase.service.impl;

import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.constant.SortByDataConstant;
import com.example.projectbase.constant.SuccessMessage;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.pagination.PagingMeta;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.entity.Banner;
import com.example.projectbase.exception.NotFoundException;
import com.example.projectbase.repository.BannerRepository;
import com.example.projectbase.service.BannerService;
import com.example.projectbase.util.PaginationUtil;
import com.example.projectbase.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public PaginationResponseDto<Banner> getBanners(PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.BANNER);
        Page<Banner> page = bannerRepository.getBanners(pageable);

        PaginationResponseDto<Banner> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());

        PagingMeta pagingMeta = new PagingMeta(page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize(), requestDto.getSortBy(), requestDto.getIsAscending().toString());
        responseDto.setMeta(pagingMeta);
        return responseDto;
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
            banner.setViewOrder(bannerDto.getViewOrder());
        }
        return bannerRepository.save(banner);
    }
}
