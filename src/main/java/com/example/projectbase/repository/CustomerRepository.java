package com.example.projectbase.repository;

import com.example.projectbase.domain.dto.CustomerDto;
import com.example.projectbase.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.name=?2, c.phonenumber=?3, c.address=?4, c.lastModifiedDate=CURRENT_TIMESTAMP WHERE c.id=?1")
    void  updateCustomer(int id,String name,String phoneNumber,String address );
}
