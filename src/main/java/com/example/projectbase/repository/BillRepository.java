package com.example.projectbase.repository;

import com.example.projectbase.domain.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Bill b SET b.status=?3,b.lastModifiedDate=CURRENT_TIMESTAMP WHERE b.id=?2 AND b.customer.id=?1 ")
    void updateStatus(int customerId, int billId, String status);

    @Query("SELECT b FROM Bill b WHERE b.customer.id=?1")
    Page<Bill> getBills(int customerId, Pageable pageable);

    @Query("SELECT COUNT(b) FROM Bill b WHERE b.status = 'Đặt hàng thành công'")
    int countBill();

    @Query("SELECT SUM(b.total) FROM Bill b WHERE b.status = 'Đã giao'")
    long getRevenue();

    @Query("SELECT COUNT(b) FROM Bill b WHERE b.status='Chờ xử lý'")
    int getCountBillToPay();

    @Query("SELECT b FROM Bill b WHERE b.status='Chờ xử lý' ORDER BY b.lastModifiedDate DESC")
    List<Bill> getBillsToPay();

    @Query("SELECT b FROM Bill b ")
    Page getAllBill(Pageable pageable);

    @Query("SELECT b FROM Bill  b WHERE b.status=?1")
    Page getBillsByStatus(String status, Pageable pageable);

    @Query("SELECT b FROM Bill b WHERE b.createdDate >= ?1 AND b.createdDate <= ?2 ")
    List<Bill> getBillStatistics(LocalDateTime timeStart, LocalDateTime timeEnd);
}
