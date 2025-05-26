package com.yl.musinsa.controller;

import com.yl.musinsa.dto.LowPriceDto;
import com.yl.musinsa.dto.LowPriceResponse;
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


    @GetMapping("/category/brand-low-price")
    public LowPriceResponse getLowPriceBrand() {
        List<LowPriceDto> list = productService.findByLowPriceCategoryBrand();
        BigDecimal sum = list.stream().map(LowPriceDto::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return new LowPriceResponse(list, sum);
    }

}
