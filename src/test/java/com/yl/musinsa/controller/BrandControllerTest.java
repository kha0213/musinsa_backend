package com.yl.musinsa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yl.musinsa.config.JpaAuditingConfig;
import com.yl.musinsa.config.QueryDslConfig;
import com.yl.musinsa.controller.api.BrandApiController;
import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.service.BrandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BrandApiController.class,
           excludeFilters = {
               @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                   QueryDslConfig.class,
                   JpaAuditingConfig.class
               })
           })
@DisplayName("BrandApiController 테스트")
class BrandApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BrandService brandService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/brands - 모든 브랜드 조회")
    void getAllBrands() throws Exception {
        // given
        List<Brand> brands = Arrays.asList(
                Brand.create("A", "브랜드A"),
                Brand.create("B", "브랜드B")
        );
        given(brandService.getAllBrands()).willReturn(brands);

        // when & then
        mockMvc.perform(get("/api/brands"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[1].name").value("B"));
    }

    @Test
    @DisplayName("POST /api/brands - 브랜드 등록 성공")
    void registerBrand_Success() throws Exception {
        // given
        Brand savedBrand = Brand.create("NewBrand", "새로운 브랜드");
        given(brandService.registerBrand("NewBrand", "새로운 브랜드"))
                .willReturn(savedBrand);

        // when & then
        mockMvc.perform(post("/api/brands")
                        .param("name", "NewBrand")
                        .param("description", "새로운 브랜드"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("NewBrand"))
                .andExpect(jsonPath("$.description").value("새로운 브랜드"));
    }
}
