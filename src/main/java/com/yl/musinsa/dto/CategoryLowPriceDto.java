package com.yl.musinsa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryLowPriceDto {
    private String categoryName;
    private String brandName;
    private BigDecimal price;
}
