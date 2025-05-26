package com.yl.musinsa.service;

import com.yl.musinsa.dto.BrandCategoryResponse;
import com.yl.musinsa.dto.LowPriceDto;
import com.yl.musinsa.dto.ProductSaveRequest;
import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.entity.Category;
import com.yl.musinsa.entity.Product;
import com.yl.musinsa.exception.ErrorCode;
import com.yl.musinsa.exception.MusinsaException;
import com.yl.musinsa.repository.BrandRepository;
import com.yl.musinsa.repository.CategoryRepository;
import com.yl.musinsa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

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
    
    /**
     * 브랜드별 상품 일괄 저장/수정
     */
    @Transactional
    public void saveProductsByBrand(Long brandId, List<ProductSaveRequest> saveRequests) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new MusinsaException(ErrorCode.BRAND_NOT_FOUND));
        Map<Long, Product> categoryProductMap = productRepository.findByBrand(brand)
                .stream().collect(Collectors.toMap(i -> i.getCategory().getId(), Function.identity()));

        for (ProductSaveRequest request : saveRequests) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new MusinsaException(ErrorCode.CATEGORY_NOT_FOUND));

            Product savedProduct = categoryProductMap.get(request.getCategoryId());

            if (savedProduct == null) {
                Product product = Product.create(brand, category, request.getPrice());
                productRepository.save(product);
            } else {
                savedProduct.update(request.getPrice());
            }
        }
    }

    public List<LowPriceDto> findByLowPriceCategoryBrand() {
        return productRepository.findByLowPriceCategoryBrand();
    }
}
