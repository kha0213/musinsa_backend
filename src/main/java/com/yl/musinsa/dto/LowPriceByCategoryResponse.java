package com.yl.musinsa.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class LowPriceByCategoryResponse {
    List<LowPriceByCategoryDto> categoryLowPrices;
    BigDecimal totalPrice;
}
