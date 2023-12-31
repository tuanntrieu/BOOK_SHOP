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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product extends DateAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", insertable = false, updatable = false, nullable = false)
    private int productId;

    private String name;

    @Column(nullable = false)
    private String featuredImage;

    private String author;


    @Column(nullable = false)
    private int price;

    @Lob
    private String description;

    private float discount;

    private String size;

    private int quantity;

    @Column(columnDefinition = "INTEGER DEFAULT 0")
    private int selled;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BillDetail> billDetail;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cate_id", foreignKey = @ForeignKey(name = "FK_CATEGORY_PRODUCT"), referencedColumnName = "cate_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<CartDetail> cartDetails;

    @ManyToMany(mappedBy = "favoriteProducts", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Customer> favoriteByCustomers;
}
