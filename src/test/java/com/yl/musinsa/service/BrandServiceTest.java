package com.yl.musinsa.service;

import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.exception.MusinsaException;
import com.yl.musinsa.helper.TestHelper;
import com.yl.musinsa.repository.BrandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    @Test
    @DisplayName("브랜드 등록 - 정상")
    void saveBrand_Success() {
        // given
        String brandName = "테스트브랜드";
        String description = "테스트 설명";
        Brand savedBrand = Brand.create(brandName, description);
        TestHelper.setId(savedBrand, 1L);

        when(brandRepository.existsByName(brandName)).thenReturn(false);
        when(brandRepository.save(any(Brand.class))).thenReturn(savedBrand);

        // when
        Brand result = brandService.saveBrand(brandName, description);

        // then
        assertThat(result.getName()).isEqualTo(brandName);
        assertThat(result.getDescription()).isEqualTo(description);
    }

    @Test
    @DisplayName("브랜드 등록 - 중복 이름")
    void saveBrand_DuplicateName() {
        // given
        String brandName = "중복브랜드";
        when(brandRepository.existsByName(brandName)).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> brandService.saveBrand(brandName, "설명"))
                .isInstanceOf(MusinsaException.class);
    }

    @Test
    @DisplayName("모든 브랜드 조회")
    void findAll() {
        // given
        Brand brandA = Brand.create( "A", "A");
        Brand brandB = Brand.create( "B", "B");
        TestHelper.setId(brandA, 1L);
        TestHelper.setId(brandB, 1L);
        when(brandRepository.findAll()).thenReturn(List.of(brandA, brandB));

        // when
        List<Brand> result = brandService.getAllBrands();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("A");
        assertThat(result.get(1).getName()).isEqualTo("B");
    }
}
