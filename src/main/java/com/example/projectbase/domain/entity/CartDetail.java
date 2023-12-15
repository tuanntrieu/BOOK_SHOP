package com.example.projectbase.domain.entity;


import com.example.projectbase.domain.entity.common.DateAuditing;
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
public class CartDetail extends DateAuditing {
    public CartDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_detail_id")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "FK_CART_PRODUCT"), referencedColumnName = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", foreignKey = @ForeignKey(name = "FK_CART_PRODUCT1"), referencedColumnName = "cart_id")
    private Cart cart;

    private int quantity;

}
