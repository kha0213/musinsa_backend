package com.yl.musinsa.repository;

import com.yl.musinsa.dto.BrandPriceDto;
import com.yl.musinsa.dto.LowPriceByCategoryDto;
import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.entity.Category;
import com.yl.musinsa.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    private Brand brandA, brandB;
    private Category category1, category2;

    @BeforeEach
    void setUp() {
        // 브랜드 생성
        brandA = Brand.create("A", "브랜드 A");
        brandB = Brand.create("B", "브랜드 B");
        entityManager.persist(brandA);
        entityManager.persist(brandB);

        // 카테고리 생성
        category1 = Category.create("상의");
        category2 = Category.create("아우터");
        entityManager.persist(category1);
        entityManager.persist(category2);

        // 상품 생성
        Product product1 = Product.create(brandA, category1, new BigDecimal("10000"));
        Product product2 = Product.create(brandB, category1, new BigDecimal("11000"));
        Product product3 = Product.create(brandA, category2, new BigDecimal("5000"));
        Product product4 = Product.create(brandB, category2, new BigDecimal("4000"));

        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.persist(product3);
        entityManager.persist(product4);
        entityManager.flush();
    }

    @Test
    @DisplayName("카테고리별 최저가 브랜드 조회")
    void findByLowestPriceCategory() {
        // when
        List<LowPriceByCategoryDto> result = productRepository.findByLowestPriceCategory();

        // then
        assertThat(result).hasSize(2);
        
        // 상의 최저가는 브랜드 A (10000원)
        LowPriceByCategoryDto topResult = result.stream()
                .filter(dto -> "상의".equals(dto.getCategoryName()))
                .findFirst().orElse(null);
        assertThat(topResult).isNotNull();
        assertThat(topResult.getBrandName()).isEqualTo("A");
        assertThat(topResult.getPrice()).isEqualTo(new BigDecimal("10000"));

        // 아우터 최저가는 브랜드 B (4000원)
        LowPriceByCategoryDto outerResult = result.stream()
                .filter(dto -> "아우터".equals(dto.getCategoryName()))
                .findFirst().orElse(null);
        assertThat(outerResult).isNotNull();
        assertThat(outerResult.getBrandName()).isEqualTo("B");
        assertThat(outerResult.getPrice()).isEqualTo(new BigDecimal("4000"));
    }

    @Test
    @DisplayName("단일 브랜드 최저가 총합 조회")
    void findLowestBrand() {
        // when
        Brand result = productRepository.findLowestBrand();

        // then
        // 브랜드 B가 총합 15000원으로 더 저렴함 (11000 + 4000)
        // 브랜드 A는 총합 15000원 (10000 + 5000)
        assertThat(result).isNotNull();
        assertThat(result.getName()).isIn("A", "B"); // 둘 다 같은 총합이므로 둘 중 하나
    }

    @Test
    @DisplayName("특정 카테고리의 최저가 브랜드들 조회")
    void findLowestPriceByCategory() {
        // when
        List<BrandPriceDto> result = productRepository.findLowestPriceByCategory(category1.getId());

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBrandName()).isEqualTo("A");
        assertThat(result.get(0).getPrice()).isEqualTo(new BigDecimal("10000"));
    }

    @Test
    @DisplayName("특정 카테고리의 최고가 브랜드들 조회")
    void findHighestPriceByCategory() {
        // when
        List<BrandPriceDto> result = productRepository.findHighestPriceByCategory(category1.getId());

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBrandName()).isEqualTo("B");
        assertThat(result.get(0).getPrice()).isEqualTo(new BigDecimal("11000"));
    }
}
