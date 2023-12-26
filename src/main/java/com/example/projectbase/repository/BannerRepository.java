package com.example.projectbase.repository;

import com.example.projectbase.domain.entity.Banner;
import com.example.projectbase.domain.entity.Customer;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Banner b SET b.image=?2,b.lastModifiedDate=CURRENT_TIMESTAMP WHERE b.image=?1")
    void updateBanner(int bannerId, String image);

    @Query("SELECT b FROM Banner b")
    Page<Banner> getBanners(Pageable pageable);


}
