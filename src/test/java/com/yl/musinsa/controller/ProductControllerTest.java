package com.yl.musinsa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yl.musinsa.dto.ProductSaveRequest;
import com.yl.musinsa.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@DisplayName("ProductController 테스트")
class ProductControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private ProductService productService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @DisplayName("브랜드별 상품 저장 성공")
    void saveProductsByBrand_Success() throws Exception {
        // given
        Long brandId = 1L;
        List<ProductSaveRequest> products = Arrays.asList(
                new ProductSaveRequest(1L, new BigDecimal("10000")),
                new ProductSaveRequest(2L, new BigDecimal("20000"))
        );
        
        doNothing().when(productService).saveProductsByBrand(eq(brandId), any());
        
        // when & then
        mockMvc.perform(post("/api/products/brand/{brandId}", brandId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(products)))
                .andExpect(status().isOk())
                .andExpect(content().string("상품이 성공적으로 저장되었습니다."));
        
        verify(productService).saveProductsByBrand(eq(brandId), any());
    }
    
    @Test
    @DisplayName("빈 상품 목록으로 저장 요청")
    void saveProductsByBrand_EmptyList() throws Exception {
        // given
        Long brandId = 1L;
        List<ProductSaveRequest> emptyProducts = Arrays.asList();
        
        doNothing().when(productService).saveProductsByBrand(eq(brandId), any());
        
        // when & then
        mockMvc.perform(post("/api/products/brand/{brandId}", brandId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyProducts)))
                .andExpect(status().isOk())
                .andExpect(content().string("상품이 성공적으로 저장되었습니다."));
        
        verify(productService).saveProductsByBrand(eq(brandId), any());
    }
}
