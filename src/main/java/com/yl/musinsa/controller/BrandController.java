package com.yl.musinsa.controller.api;

import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 브랜드 관리 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BrandApiController {
    
    private final BrandService brandService;
    
    /**
     * 모든 브랜드 조회
     */
    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }
    
    /**
     * 브랜드 등록
     */
    @PostMapping
    public ResponseEntity<Brand> registerBrand(@RequestParam String name,
                                             @RequestParam(required = false) String description) {
        Brand brand = brandService.registerBrand(name, description);
        return ResponseEntity.ok(brand);
    }
}
