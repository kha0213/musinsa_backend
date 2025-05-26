package com.yl.musinsa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 브랜드별 카테고리별 상품 가격 정보
 * 각 브랜드는 카테고리별로 하나의 상품만 판매한다고 가정
 */
@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    @Column(nullable = false, precision = 10)
    private BigDecimal price;
    
    @Column
    private String name;
    
    public static Product create(Brand brand, Category category, BigDecimal price, String name) {
        Product product = new Product();
        product.brand = brand;
        product.category = category;
        product.price = price;
        product.name = name;
        return product;
    }

    public void update(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
