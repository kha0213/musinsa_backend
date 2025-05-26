package com.yl.musinsa.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class LowPriceResponse {
    List<LowPriceDto> categoryLowPrices;
    BigDecimal totalPrice;
}
