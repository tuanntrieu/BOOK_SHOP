package com.example.projectbase.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_detail_id")
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id",foreignKey = @ForeignKey(name = "FK_BILL_PRODUCT1"),referencedColumnName = "product_id")
    private Product product;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnore
    @JoinColumn(name = "bill_id",foreignKey = @ForeignKey(name = "FK_BILL_PRODUCT"),referencedColumnName = "bill_id")
    private Bill bill;

    @Column(nullable = false)
    private int quantity;

    public BillDetail(Product product, Bill bill, int quantity) {
        this.product = product;
        this.bill = bill;
        this.quantity = quantity;
    }
}
