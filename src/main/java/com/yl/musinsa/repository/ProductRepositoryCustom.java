package com.yl.musinsa.repository;

import com.yl.musinsa.dto.LowPriceDto;

import java.util.List;

public interface ProductRepositoryCustom {
    List<LowPriceDto> findByLowPriceCategoryBrand();
}
