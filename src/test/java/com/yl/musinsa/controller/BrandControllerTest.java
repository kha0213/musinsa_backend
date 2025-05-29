package com.yl.musinsa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yl.musinsa.dto.BrandCreateRequest;
import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.exception.ErrorCode;
import com.yl.musinsa.exception.MusinsaException;
import com.yl.musinsa.helper.TestHelper;
import com.yl.musinsa.service.BrandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandController.class)
class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BrandService brandService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("브랜드 등록 API - 정상")
    void createBrand_Success() throws Exception {
        // given
        String name = "테스트브랜드";
        String description = "설명";
        BrandCreateRequest brandCreateRequest = new BrandCreateRequest();
        brandCreateRequest.setName(name);
        brandCreateRequest.setDescription(description);
        Brand savedBrand = Brand.create( name, description);
        TestHelper.setId(savedBrand, 1L);
        when(brandService.saveBrand(name, description)).thenReturn(savedBrand);

        // when & then
        mockMvc.perform(post("/api/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandCreateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("테스트브랜드"))
                .andExpect(jsonPath("$.description").value("설명"));
    }

    @Test
    @DisplayName("브랜드 등록 API - 중복 이름")
    void createBrand_DuplicateName() throws Exception {
        // given
        BrandCreateRequest request = new BrandCreateRequest("중복브랜드", "설명");
        
        when(brandService.saveBrand(anyString(), anyString()))
                .thenThrow(new MusinsaException(ErrorCode.DUPLICATE_NAME));

        // when & then
        mockMvc.perform(post("/api/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("이미 등록된 이름입니다."));
    }

    @Test
    @DisplayName("브랜드 등록 API - 빈 이름")
    void createBrand_BlankName() throws Exception {
        // given
        BrandCreateRequest request = new BrandCreateRequest("", "설명");

        // when & then
        mockMvc.perform(post("/api/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("모든 브랜드 조회 API")
    void getAllBrands() throws Exception {
        // given
        Brand brand1 = Brand.create( "A", "브랜드 A");
        TestHelper.setId(brand1, 1L);
        Brand brand2 = Brand.create( "B", "브랜드 B");
        TestHelper.setId(brand2, 2L);
        when(brandService.getAllBrands()).thenReturn(List.of(brand1, brand2));

        // when & then
        mockMvc.perform(get("/api/brands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[1].name").value("B"));
    }
}
