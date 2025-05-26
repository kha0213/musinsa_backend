package com.yl.musinsa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LowPriceDto {
    private Long categoryId;
    private String categoryName;
    private Long brandId;
    private String brandName;
    private Long productId;
    private BigDecimal price;
}
