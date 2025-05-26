package com.yl.musinsa.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 상품 등록/수정 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaveRequest {
    @NotNull
    private Long categoryId;
    @NotNull
    private String productName;
    private BigDecimal price;
}
