package com.example.projectbase.repository;

import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.entity.Customer;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.name=?2, c.phonenumber=?3, c.address=?4, c.lastModifiedDate=CURRENT_TIMESTAMP, c.avatar=?5 WHERE c.id=?1")
    void updateCustomer(int id, String name, String phoneNumber, String address, String avatar);

    @Query("SELECT c FROM Customer c WHERE (c.name LIKE %:keyword%)")
    Page<Customer> getCustomers(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE c.user.id=?1 ")
    Optional<Customer> getCustomerByUserId(String userId);

    @Query("SELECT new com.example.projectbase.domain.dto.response.GetProductsResponseDto(p.productId, p.name, p.featuredImage, p.price, p.discount, p.quantity,p.category) FROM Customer c JOIN c.favoriteProducts p WHERE c.id = ?1")
    Page<GetProductsResponseDto> getFavoriteProducts(int customerId, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Customer c")
    int countCustomer();
}
