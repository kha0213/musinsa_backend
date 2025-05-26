package com.yl.musinsa.service;

import com.yl.musinsa.dto.BrandCategoryResponse;
import com.yl.musinsa.dto.ProductSaveRequest;
import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.entity.Category;
import com.yl.musinsa.entity.Product;
import com.yl.musinsa.exception.ErrorCode;
import com.yl.musinsa.exception.MusinsaException;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.yl.musinsa.helper.TestHelper.setId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService 테스트")
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
    
    private Brand brand;
    private Category category1, category2;
    private Product product1, product2;
    
    @BeforeEach
    void setUp() {
        brand = Brand.create("테스트브랜드", "테스트 설명");
        category1 = Category.create("상의");
        setId(category1, 1L);
        category2 = Category.create("아우터");
        setId(category2, 2L);

        product1 = Product.create(brand, category1, new BigDecimal("10000"));
        setId(product1, 1L);
        product2 = Product.create(brand, category2, new BigDecimal("20000"));
        setId(product2, 2L);
    }
    
    @Test
    @DisplayName("모든 브랜드 카테고리 조회 성공")
    void findAllBrandCategory_Success() {
        // given
        List<Brand> brands = Arrays.asList(brand);
        List<Product> products = Arrays.asList(product1, product2);
        
        when(brandRepository.findAll()).thenReturn(brands);
        when(productRepository.findByBrandInOrderByBrand_NameAsc(brands)).thenReturn(products);
        
        // when
        List<BrandCategoryResponse> result = productService.findAllBrandCategory();
        
        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBrandName()).isEqualTo("테스트브랜드");
        assertThat(result.get(0).getCategoryProducts()).hasSize(2);
    }
    
    @Test
    @DisplayName("브랜드가 없을 때 빈 목록 반환")
    void findAllBrandCategory_EmptyBrands() {
        // given
        when(brandRepository.findAll()).thenReturn(Arrays.asList());
        when(productRepository.findByBrandInOrderByBrand_NameAsc(any())).thenReturn(Arrays.asList());
        
        // when
        List<BrandCategoryResponse> result = productService.findAllBrandCategory();
        
        // then
        assertThat(result).isEmpty();
    }
    
    @Test
    @DisplayName("브랜드별 상품 저장 - 새 상품 생성")
    void saveProductsByBrand_CreateNewProduct() {
        // given
        Long brandId = 1L;
        List<ProductSaveRequest> requests = Arrays.asList(
                new ProductSaveRequest(1L, new BigDecimal("15000"))
        );
        
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        when(productRepository.findByBrand(brand)).thenReturn(Arrays.asList());
        when(productRepository.save(any(Product.class))).thenReturn(product1);
        
        // when
        productService.saveProductsByBrand(brandId, requests);
        
        // then
        verify(productRepository).save(any(Product.class));
    }
    
    @Test
    @DisplayName("브랜드별 상품 저장 - 기존 상품 업데이트")
    void saveProductsByBrand_UpdateExistingProduct() {
        // given
        Long brandId = 1L;
        List<ProductSaveRequest> requests = Arrays.asList(
                new ProductSaveRequest(1L, new BigDecimal("15000"))
        );
        
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        when(productRepository.findByBrand(brand)).thenReturn(Arrays.asList(product1));
        
        // when
        productService.saveProductsByBrand(brandId, requests);
        
        // then
        verify(productRepository, never()).save(any(Product.class));
    }
    
    @Test
    @DisplayName("브랜드별 상품 저장 - 브랜드 없음 예외")
    void saveProductsByBrand_BrandNotFound() {
        // given
        Long brandId = 999L;
        List<ProductSaveRequest> requests = Arrays.asList(
                new ProductSaveRequest(1L, new BigDecimal("15000"))
        );
        
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());
        
        // when & then
        assertThatThrownBy(() -> productService.saveProductsByBrand(brandId, requests))
                .isInstanceOf(MusinsaException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.BRAND_NOT_FOUND);
    }
    
    @Test
    @DisplayName("브랜드별 상품 저장 - 카테고리 없음 예외")
    void saveProductsByBrand_CategoryNotFound() {
        // given
        Long brandId = 1L;
        List<ProductSaveRequest> requests = Arrays.asList(
                new ProductSaveRequest(999L, new BigDecimal("15000"))
        );
        
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());
        when(productRepository.findByBrand(brand)).thenReturn(Arrays.asList());
        
        // when & then
        assertThatThrownBy(() -> productService.saveProductsByBrand(brandId, requests))
                .isInstanceOf(MusinsaException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.CATEGORY_NOT_FOUND);
    }
}
