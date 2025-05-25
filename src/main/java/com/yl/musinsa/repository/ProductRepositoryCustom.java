package com.yl.musinsa.repository;

import com.yl.musinsa.dto.BrandCategoryResponse;

import java.util.List;

public interface ProductRepositoryCustom {
    
    List<BrandCategoryResponse> findAllBrandCategory();
}
