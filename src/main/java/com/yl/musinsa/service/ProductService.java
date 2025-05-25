package com.yl.musinsa.service;

import com.yl.musinsa.dto.BrandCategoryResponse;
import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.entity.Product;
import com.yl.musinsa.repository.BrandRepository;
import com.yl.musinsa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    /**
     * 카테고리별 상품 조회
     */
    public List<BrandCategoryResponse> findAllBrandCategory() {
        List<Brand> brands = brandRepository.findAll();
        List<Product> products = productRepository.findByBrandInOrderByBrand_NameAsc(brands);
        Map<Brand, List<Product>> brandProducts = products
                .stream()
                .collect(Collectors.groupingBy(Product::getBrand));

        return brands.stream()
                .map(it -> BrandCategoryResponse.of(it, brandProducts.getOrDefault(it, new ArrayList<>())))
                .toList();
    }
}
