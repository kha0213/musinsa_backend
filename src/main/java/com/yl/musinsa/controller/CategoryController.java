package com.yl.musinsa.controller;

import com.yl.musinsa.dto.CategoryDto;
import com.yl.musinsa.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 카테고리 관리 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    
    /**
     * 모든 카테고리 조회
     */
    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories()
                .stream()
                .map(CategoryDto::of)
                .toList();
    }
}
