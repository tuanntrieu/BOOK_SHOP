package com.example.projectbase.domain.entity;


import com.example.projectbase.domain.entity.common.DateAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "bills")
public class Bill extends DateAuditing {

    @Id
    @Column(name = "bill_id",insertable = false, updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nameCustomer;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private int feeShip;

    @OneToMany(mappedBy = "bill",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BillDetail> billDetail;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "customer_id",foreignKey = @ForeignKey(name = "FK_CUSTOMER_BILL"),referencedColumnName = "customer_id")
    private Customer customer;


}
