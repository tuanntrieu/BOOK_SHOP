package com.example.projectbase.repository;

import com.example.projectbase.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE Cart c SET c.customer.id = ?2 WHERE c.id = ?1")
    void addCartForCustomer(int id, int customerId);



}
