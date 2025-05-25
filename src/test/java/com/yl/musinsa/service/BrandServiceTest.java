package com.yl.musinsa.service;

import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.exception.MusinsaException;
import com.yl.musinsa.repository.BrandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("BrandService 단위 테스트")
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    @Test
    @DisplayName("브랜드 등록 - 성공")
    void registerBrand_Success() {
        // given
        String name = "NewBrand";
        String description = "새로운 브랜드";
        Brand savedBrand = Brand.create(name, description);
        
        given(brandRepository.existsByName(name)).willReturn(false);
        given(brandRepository.save(any(Brand.class))).willReturn(savedBrand);

        // when
        Brand result = brandService.registerBrand(name, description);

        // then
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getDescription()).isEqualTo(description);
        verify(brandRepository).existsByName(name);
        verify(brandRepository).save(any(Brand.class));
    }

    @Test
    @DisplayName("브랜드 등록 - 중복 이름으로 실패")
    void registerBrand_DuplicateName_ThrowsException() {
        // given
        String duplicateName = "ExistingBrand";
        given(brandRepository.existsByName(duplicateName)).willReturn(true);

        // when & then
        assertThatThrownBy(() -> brandService.registerBrand(duplicateName, "설명"))
                .isInstanceOf(MusinsaException.class);
        
        verify(brandRepository).existsByName(duplicateName);
    }

    @Test
    @DisplayName("모든 브랜드 조회")
    void getAllBrands() {
        // given
        List<Brand> brands = Arrays.asList(
                Brand.create("A", "브랜드A"),
                Brand.create("B", "브랜드B")
        );
        given(brandRepository.findAll()).willReturn(brands);

        // when
        List<Brand> result = brandService.getAllBrands();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("A");
        assertThat(result.get(1).getName()).isEqualTo("B");
        verify(brandRepository).findAll();
    }
}
