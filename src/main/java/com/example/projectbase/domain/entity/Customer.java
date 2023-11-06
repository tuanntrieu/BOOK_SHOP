package com.example.projectbase.domain.entity;

import com.example.projectbase.domain.entity.common.DateAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Customer extends DateAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int id;

    @Nationalized
    @Column(nullable = false)
    private String name;
    
    private String phonenumber;

    private String address;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
    @JsonIgnore
    private Cart cart;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Bill> bill = new ArrayList<>();

}
