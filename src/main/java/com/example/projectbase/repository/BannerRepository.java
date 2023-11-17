package com.example.projectbase.repository;

import com.example.projectbase.domain.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner,Integer> {

    @Query("SELECT  b FROM Banner b ORDER BY b.createdDate ")
    List<Banner> getBanners();

}
