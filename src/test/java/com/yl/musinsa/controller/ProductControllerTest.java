package com.yl.musinsa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yl.musinsa.dto.CategoryPriceRangeResponse;
import com.yl.musinsa.dto.LowPriceByBrandResponse;
import com.yl.musinsa.dto.LowPriceByCategoryDto;
import com.yl.musinsa.dto.LowPriceByCategoryResponse;
import com.yl.musinsa.exception.ErrorCode;
import com.yl.musinsa.exception.MusinsaException;
import com.yl.musinsa.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("카테고리별 최저가 조회 API")
    void getLowestPriceByCategory() throws Exception {
        // given
        LowPriceByCategoryDto dto = new LowPriceByCategoryDto();
        dto.setCategoryName("상의");
        dto.setBrandName("C");
        dto.setPrice(new BigDecimal("10000"));

        LowPriceByCategoryResponse response = new LowPriceByCategoryResponse(
                List.of(dto), new BigDecimal("10000")
        );

        when(productService.findByLowestPriceCategory()).thenReturn(List.of(dto));

        // when & then
        mockMvc.perform(get("/api/products/lowest-by-category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryLowPrices[0].categoryName").value("상의"))
                .andExpect(jsonPath("$.categoryLowPrices[0].brandName").value("C"))
                .andExpect(jsonPath("$.totalPrice").value(10000));
    }

    @Test
    @DisplayName("단일 브랜드 최저가 조회 API")
    void getLowestBrandTotal() throws Exception {
        // given
        LowPriceByBrandResponse response = LowPriceByBrandResponse.builder()
                .sum(new BigDecimal("36100"))
                .build();

        when(productService.findByLowestBrandTotal()).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/products/lowest-brand-total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum").value(36100));
    }

    @Test
    @DisplayName("카테고리별 가격대 조회 API - 정상")
    void getCategoryPriceRange_Success() throws Exception {
        // given
        CategoryPriceRangeResponse response = new CategoryPriceRangeResponse(
                "상의", List.of(), List.of()
        );

        when(productService.findCategoryPriceRange("상의")).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/products/category/price-range")
                        .param("categoryName", "상의"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("상의"));
    }

    @Test
    @DisplayName("카테고리별 가격대 조회 API - 존재하지 않는 카테고리")
    void getCategoryPriceRange_CategoryNotFound() throws Exception {
        // given
        when(productService.findCategoryPriceRange(anyString()))
                .thenThrow(new MusinsaException(ErrorCode.CATEGORY_NOT_FOUND));

        // when & then
        mockMvc.perform(get("/api/products/category/price-range")
                        .param("categoryName", "없는카테고리"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("카테고리를 찾을 수 없습니다."));
    }

    @Test
    @DisplayName("카테고리별 가격대 조회 API - 빈 카테고리명")
    void getCategoryPriceRange_BlankCategoryName() throws Exception {
        // when & then
        mockMvc.perform(get("/api/products/category/price-range")
                        .param("categoryName", ""))
                .andExpect(status().isBadRequest());
    }
}
