package com.yl.musinsa.dto;

import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.entity.Product;
import lombok.Value;

import java.util.List;

/**
 * 브랜드별 카테고리 상품 조회 응답 DTO
 */
@Value(staticConstructor = "of")
public class BrandCategoryResponse {
    Long brandId;
    String brandName;
    List<CategoryProduct> categoryProducts;

    public static BrandCategoryResponse of(Brand brand, List<Product> products) {
        List<CategoryProduct> list = products.stream().map(CategoryProduct::of).toList();
        return new BrandCategoryResponse(brand.getId(), brand.getName(), list);
    }
}
