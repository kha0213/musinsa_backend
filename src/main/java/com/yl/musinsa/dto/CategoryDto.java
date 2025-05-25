package com.yl.musinsa.dto;

import com.yl.musinsa.entity.Category;
import lombok.Value;

@Value(staticConstructor = "of")
public class CategoryDto {
    Long id;
    String name;

    public static CategoryDto of(Category category) {
        return CategoryDto.of(category.getId(), category.getName());
    }
}
