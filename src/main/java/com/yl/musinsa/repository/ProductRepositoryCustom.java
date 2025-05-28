package com.yl.musinsa.repository;

import com.yl.musinsa.dto.BrandPriceDto;
import com.yl.musinsa.dto.LowPriceByCategoryDto;
import com.yl.musinsa.entity.Brand;

import java.util.List;

public interface ProductRepositoryCustom {
    List<LowPriceByCategoryDto> findByLowestPriceCategory();
    Brand findLowestBrand();
    List<BrandPriceDto> findLowestPriceByCategory(Long categoryId);
    List<BrandPriceDto> findHighestPriceByCategory(Long categoryId);
}
