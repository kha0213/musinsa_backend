package com.yl.musinsa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LowPriceByBrandResponse {
    private BrandDto brand;
    private List<CategoryProduct> categoryProducts;
    private BigDecimal sum;
}
