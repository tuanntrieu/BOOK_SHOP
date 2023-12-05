package com.example.projectbase.repository;

import com.example.projectbase.domain.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Bill b SET b.status=?3 WHERE b.id=?2 AND b.customer.id=?1 ")
    void updateStatus(int customerId,int billId, String status);

    @Query("SELECT b FROM Bill b WHERE b.customer.id=?1")
    Page<Bill> getBills(int customerId, Pageable pageable);
}
