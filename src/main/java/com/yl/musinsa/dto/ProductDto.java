package com.yl.musinsa.dto;

import com.yl.musinsa.entity.Product;
import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
public class ProductDto {
    Long id;
    BigDecimal price;
    String name;

    public static ProductDto of(Product product) {
        return of(product.getId(), product.getPrice(), product.getName());
    }
}
