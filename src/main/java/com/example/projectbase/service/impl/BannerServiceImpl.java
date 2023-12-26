package com.example.projectbase.service.impl;

import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.constant.SuccessMessage;
import com.example.projectbase.domain.dto.BannerDto;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;
    private final UploadFileUtil uploadFileUtil;


    @Override
    public PaginationResponseDto getBanners(PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto);
        Page<Banner> page = bannerRepository.getBanners(pageable);
        PaginationResponseDto<Banner> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        PagingMeta pagingMeta = new PagingMeta(page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize(), requestDto.getSortBy(), requestDto.getIsAscending().toString());
        responseDto.setMeta(pagingMeta);
        return responseDto;
    }

    @Override
    public CommonResponseDto updateBanner(int bannerId, BannerDto bannerDto) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Banner.ERR_NOT_FOUND_ID, new String[]{String.valueOf(bannerId)}));
        uploadFileUtil.destroyFileWithUrl(banner.getImage());
        bannerRepository.updateBanner(banner.getId(), uploadFileUtil.uploadFile(bannerDto.getMultipartFile()));
        return new CommonResponseDto(true, SuccessMessage.UPDATE);
    }

    @Override
    public CommonResponseDto deleteBanner(int bannerId) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Banner.ERR_NOT_FOUND_ID, new String[]{String.valueOf(bannerId)}));
        bannerRepository.delete(banner);
        return new CommonResponseDto(true, SuccessMessage.DELETE);
    }

    @Override
    public Banner createBanner(BannerDto bannerDto) {
        Banner banner = new Banner();
        banner.setImage(uploadFileUtil.uploadFile(bannerDto.getMultipartFile()));
        return bannerRepository.save(banner);
    }
}
