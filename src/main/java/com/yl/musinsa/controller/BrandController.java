package com.yl.musinsa.controller;

import com.yl.musinsa.dto.BrandCreateRequest;
import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 브랜드 관리 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {
    
    private final BrandService brandService;
    
    /**
     * 모든 브랜드 조회
     */
    @GetMapping
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }
    
    /**
     * 브랜드 등록
     */
    @PostMapping
    public Brand registerBrand(@RequestBody @Valid BrandCreateRequest request) {
        return brandService.saveBrand(request.getName(), request.getDescription());
    }
}
