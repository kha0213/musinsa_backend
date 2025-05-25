package com.yl.musinsa.repository;

import com.yl.musinsa.entity.Brand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@DisplayName("BrandRepository 테스트")
class BrandRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    @DisplayName("브랜드명으로 조회 - 존재하는 경우")
    void findByName_ExistingBrand() {
        // given
        Brand brand = Brand.create("TestBrand", "테스트 브랜드");
        entityManager.persistAndFlush(brand);

        // when
        Optional<Brand> result = brandRepository.findByName("TestBrand");

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("TestBrand");
        assertThat(result.get().getDescription()).isEqualTo("테스트 브랜드");
    }

    @Test
    @DisplayName("브랜드명으로 조회 - 존재하지 않는 경우")
    void findByName_NonExistingBrand() {
        // when
        Optional<Brand> result = brandRepository.findByName("NonExistingBrand");

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("브랜드명 존재 여부 확인 - 존재하는 경우")
    void existsByName_ExistingBrand() {
        // given
        Brand brand = Brand.create("ExistingBrand", null);
        entityManager.persistAndFlush(brand);

        // when
        boolean exists = brandRepository.existsByName("ExistingBrand");

        // then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("브랜드명 존재 여부 확인 - 존재하지 않는 경우")
    void existsByName_NonExistingBrand() {
        // when
        boolean exists = brandRepository.existsByName("NonExistingBrand");

        // then
        assertThat(exists).isFalse();
    }
}
