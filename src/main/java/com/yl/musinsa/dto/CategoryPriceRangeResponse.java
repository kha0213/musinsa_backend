package com.yl.musinsa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPriceRangeResponse {
    private String category;
    private List<BrandPriceDto> lowestPrice;
    private List<BrandPriceDto> highestPrice;
}
