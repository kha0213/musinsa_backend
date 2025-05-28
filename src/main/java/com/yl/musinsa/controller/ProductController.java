package com.yl.musinsa.controller;

import com.yl.musinsa.dto.LowPriceByBrandResponse;
import com.yl.musinsa.dto.LowPriceByCategoryDto;
import com.yl.musinsa.dto.LowPriceByCategoryResponse;
import com.yl.musinsa.dto.ProductSaveRequest;
import com.yl.musinsa.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    
    /**
     * 브랜드별 상품 일괄 저장/수정
     */
    @PostMapping("/brand/{brandId}")
    public String saveProductsByBrand(
            @PathVariable Long brandId,
            @RequestBody List<ProductSaveRequest> products) {
        
        productService.saveProductsByBrand(brandId, products);
        return "상품이 성공적으로 저장되었습니다.";
    }

    /**
     * 모든 카테고리에서 가장 저렴한 브랜드의 상품 가져오기 
     */
    @GetMapping("/lowest-by-category")
    public LowPriceByCategoryResponse getLowestPriceByCategory() {
        List<LowPriceByCategoryDto> list = productService.findByLowestPriceCategory();
        BigDecimal sum = list.stream().map(LowPriceByCategoryDto::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return new LowPriceByCategoryResponse(list, sum);
    }

    /**
     * 단일 브랜드로 모두 살 때 가장 저렴한 브랜드의 상품들 가져오기
     */
    @GetMapping("/lowest-brand-total")
    public LowPriceByBrandResponse getLowestBrandTotal() {
        return productService.findByLowestBrandTotal();
    }

}
