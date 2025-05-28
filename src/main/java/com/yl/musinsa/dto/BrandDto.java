package com.yl.musinsa.dto;

import com.yl.musinsa.entity.Brand;
import lombok.Value;

@Value(staticConstructor = "of")
public class BrandDto {
    Long id;
    String name;
    String description;

    public static BrandDto of(Brand brand) {
        return new BrandDto(brand.getId(), brand.getName(), brand.getDescription());
    }
}
