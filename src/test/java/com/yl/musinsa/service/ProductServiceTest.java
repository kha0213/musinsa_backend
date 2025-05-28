package com.yl.musinsa.service;

import com.yl.musinsa.dto.CategoryPriceRangeResponse;
import com.yl.musinsa.dto.LowPriceByBrandResponse;
import com.yl.musinsa.dto.LowPriceByCategoryDto;
import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.entity.Category;
import com.yl.musinsa.exception.MusinsaException;
import com.yl.musinsa.helper.TestHelper;
import com.yl.musinsa.repository.BrandRepository;
import com.yl.musinsa.repository.CategoryRepository;
import com.yl.musinsa.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductService productService;

    private Category category;
    private Brand brand;

    @BeforeEach
    void setUp() {
        category = Category.create("상의");
        TestHelper.setId(category, 1L);
        brand = Brand.create("A", "브랜드 A");
        TestHelper.setId(brand, 1L);
    }

    @Test
    @DisplayName("카테고리별 최저가 조회 - 정상 케이스")
    void findByLowestPriceCategory_Success() {
        // given
        LowPriceByCategoryDto dto = new LowPriceByCategoryDto();
        dto.setCategoryId(1L);
        dto.setCategoryName("상의");
        dto.setBrandId(1L);
        dto.setBrandName("A");
        dto.setPrice(new BigDecimal("10000"));

        when(productRepository.findByLowestPriceCategory()).thenReturn(List.of(dto));

        // when
        List<LowPriceByCategoryDto> result = productService.findByLowestPriceCategory();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategoryName()).isEqualTo("상의");
        assertThat(result.get(0).getBrandName()).isEqualTo("A");
        assertThat(result.get(0).getPrice()).isEqualTo(new BigDecimal("10000"));
    }

    @Test
    @DisplayName("단일 브랜드 최저가 조회 - 정상 케이스")
    void findByLowestBrandTotal_Success() {
        // given
        when(productRepository.findLowestBrand()).thenReturn(brand);
        when(productRepository.findByBrand(brand)).thenReturn(List.of());

        // when
        LowPriceByBrandResponse result = productService.findByLowestBrandTotal();

        // then
        assertThat(result.getBrand().getName()).isEqualTo("A");
        assertThat(result.getSum()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("카테고리별 가격대 조회 - 정상 케이스")
    void findCategoryPriceRange_Success() {
        // given
        String categoryName = "상의";
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));
        when(productRepository.findLowestPriceByCategory(1L)).thenReturn(List.of());
        when(productRepository.findHighestPriceByCategory(1L)).thenReturn(List.of());

        // when
        CategoryPriceRangeResponse result = productService.findCategoryPriceRange(categoryName);

        // then
        assertThat(result.getCategory()).isEqualTo("상의");
        assertThat(result.getLowestPrice()).isEmpty();
        assertThat(result.getHighestPrice()).isEmpty();
    }

    @Test
    @DisplayName("카테고리별 가격대 조회 - 존재하지 않는 카테고리")
    void findCategoryPriceRange_CategoryNotFound() {
        // given
        String categoryName = "없는카테고리";
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.findCategoryPriceRange(categoryName))
                .isInstanceOf(MusinsaException.class);
    }
}
