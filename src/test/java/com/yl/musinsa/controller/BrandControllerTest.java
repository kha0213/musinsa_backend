package com.yl.musinsa.controller;

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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandController.class)
class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BrandService brandService;

    @Test
    @DisplayName("브랜드 등록 API - 정상")
    void createBrand_Success() throws Exception {
        // given
        Brand savedBrand = Brand.create( "테스트브랜드", "설명");
        TestHelper.setId(savedBrand, 1L);
        when(brandService.saveBrand("테스트브랜드", "설명")).thenReturn(savedBrand);

        MockMultipartFile nameFile = new MockMultipartFile(
                "name", "", "text/plain", "테스트브랜드".getBytes());
        MockMultipartFile descFile = new MockMultipartFile(
                "description", "", "text/plain", "설명".getBytes());

        // when & then
        mockMvc.perform(multipart("/api/brands")
                        .file(nameFile)
                        .file(descFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("테스트브랜드"))
                .andExpect(jsonPath("$.description").value("설명"));
    }

    @Test
    @DisplayName("브랜드 등록 API - 중복 이름")
    void createBrand_DuplicateName() throws Exception {
        // given
        when(brandService.saveBrand(anyString(), anyString()))
                .thenThrow(new MusinsaException(ErrorCode.DUPLICATE_NAME));

        MockMultipartFile nameFile = new MockMultipartFile(
                "name", "", "text/plain", "중복브랜드".getBytes());

        // when & then
        mockMvc.perform(multipart("/api/brands")
                        .file(nameFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("이미 등록된 이름입니다."));
    }

    @Test
    @DisplayName("모든 브랜드 조회 API")
    void getAllBrands() throws Exception {
        // given
        Brand brandA = Brand.create( "A", "A");
        Brand brandB = Brand.create( "B", "B");
        TestHelper.setId(brandA, 1L);
        TestHelper.setId(brandB, 1L);
        when(brandService.getAllBrands()).thenReturn(List.of(brandA, brandB));

        // when & then
        mockMvc.perform(get("/api/brands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[1].name").value("B"));
    }
}
