package com.yl.musinsa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryLowPriceResponse {
    private List<CategoryLowPriceDto> categoryLowPrices;
    private BigDecimal totalPrice;
}
