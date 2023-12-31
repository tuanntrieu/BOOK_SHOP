package com.example.projectbase.repository;

import com.example.projectbase.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.name=?2, c.image=?3 WHERE c.id=?1")
    void updateCategory(int categoryId, String name, String image);

}
