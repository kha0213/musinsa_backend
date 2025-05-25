package com.yl.musinsa.dto;

import com.yl.musinsa.entity.Product;
import lombok.Value;

@Value(staticConstructor = "of")
public class CategoryProduct {
    CategoryDto category;
    ProductDto product;

    public static CategoryProduct of(Product product) {
        CategoryDto categoryDto = CategoryDto.of(product.getCategory());
        ProductDto productDto = ProductDto.of(product);
        return new CategoryProduct(categoryDto, productDto);

    }
}
