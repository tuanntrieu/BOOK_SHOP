package com.example.projectbase.repository;

import com.example.projectbase.domain.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Banner b SET b.image=?2 WHERE b.image=?1")
    void updateBanner(int bannerId, String image);


}
