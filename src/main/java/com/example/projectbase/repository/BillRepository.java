package com.example.projectbase.repository;

import com.example.projectbase.domain.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Bill b SET b.status=?2 WHERE b.id=?1 ")
    void updateStatus(int billId, String status);
}
