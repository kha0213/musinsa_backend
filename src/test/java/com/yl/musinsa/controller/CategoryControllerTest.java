package com.yl.musinsa.controller;

import com.yl.musinsa.entity.Category;
import com.yl.musinsa.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@DisplayName("CategoryController 테스트")
class CategoryControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private CategoryService categoryService;
    
    @Test
    @DisplayName("모든 카테고리 조회 성공")
    void getAllCategories_Success() throws Exception {
        // given
        List<Category> categories = Arrays.asList(
                Category.create("상의"),
                Category.create("아우터"),
                Category.create("바지")
        );
        
        when(categoryService.getAllCategories()).thenReturn(categories);
        
        // when & then
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("상의")))
                .andExpect(jsonPath("$[1].name", is("아우터")))
                .andExpect(jsonPath("$[2].name", is("바지")));
    }
    
    @Test
    @DisplayName("빈 카테고리 목록 조회")
    void getAllCategories_EmptyList() throws Exception {
        // given
        when(categoryService.getAllCategories()).thenReturn(Arrays.asList());
        
        // when & then
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
