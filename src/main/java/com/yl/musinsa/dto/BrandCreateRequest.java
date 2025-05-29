package com.yl.musinsa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandCreateRequest {
    
    @NotBlank(message = "브랜드명은 필수입니다.")
    private String name;
    
    private String description;
}
