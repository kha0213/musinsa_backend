package com.yl.musinsa.service;

import com.yl.musinsa.entity.Category;
import com.yl.musinsa.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryService 테스트")
class CategoryServiceTest {
    
    @Mock
    private CategoryRepository categoryRepository;
    
    @InjectMocks
    private CategoryService categoryService;
    
    @Test
    @DisplayName("모든 카테고리 조회 성공")
    void getAllCategories_Success() {
        // given
        List<Category> expectedCategories = Arrays.asList(
                Category.create("상의"),
                Category.create("아우터"),
                Category.create("바지"),
                Category.create("스니커즈")
        );
        
        when(categoryRepository.findAll()).thenReturn(expectedCategories);
        
        // when
        List<Category> result = categoryService.getAllCategories();
        
        // then
        assertThat(result).hasSize(4);
        assertThat(result).extracting("name")
                .containsExactly("상의", "아우터", "바지", "스니커즈");
        
        verify(categoryRepository).findAll();
    }
    
    @Test
    @DisplayName("빈 카테고리 목록 조회")
    void getAllCategories_EmptyList() {
        // given
        when(categoryRepository.findAll()).thenReturn(Arrays.asList());
        
        // when
        List<Category> result = categoryService.getAllCategories();
        
        // then
        assertThat(result).isEmpty();
        verify(categoryRepository).findAll();
    }
}
