package com.yl.musinsa.service;

import com.yl.musinsa.entity.Category;
import com.yl.musinsa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    /**
     * 모든 카테고리 조회 (캐시 적용)
     */
    @Cacheable(value = "categories", key = "'all'")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
