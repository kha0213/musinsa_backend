package com.yl.musinsa.controller;

import com.yl.musinsa.dto.BrandCategoryResponse;
import com.yl.musinsa.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 홈 페이지 API 컨트롤러
 * 메인 페이지에서 필요한 데이터 제공
 */
@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {
    
    private final ProductService productService;
    
    /**
     * 홈 페이지 데이터 조회 - 모든 브랜드의 카테고리별 최저가 상품
     */
    @GetMapping("/brand-categories")
    public ResponseEntity<List<BrandCategoryResponse>> getBrandCategories() {
        List<BrandCategoryResponse> brandCategories = productService.findAllBrandCategory();
        return ResponseEntity.ok(brandCategories);
    }
}
