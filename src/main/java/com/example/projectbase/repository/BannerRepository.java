package com.example.projectbase.repository;

import com.example.projectbase.domain.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {
}
