package com.yl.musinsa.service;

import com.yl.musinsa.dto.BrandCategoryResponse;
import com.yl.musinsa.dto.BrandDto;
import com.yl.musinsa.dto.BrandPriceDto;
import com.yl.musinsa.dto.CategoryPriceRangeResponse;
import com.yl.musinsa.dto.CategoryProduct;
import com.yl.musinsa.dto.LowPriceByBrandResponse;
import com.yl.musinsa.dto.LowPriceByCategoryDto;
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

import java.math.BigDecimal;
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

    public List<LowPriceByCategoryDto> findByLowestPriceCategory() {
        return productRepository.findByLowestPriceCategory();
    }

    public LowPriceByBrandResponse findByLowestBrandTotal() {
        Brand brand = productRepository.findLowestBrand();
        List<Product> products = productRepository.findByBrand(brand);
        return LowPriceByBrandResponse.builder()
                .brand(BrandDto.of(brand))
                .sum(products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add))
                .categoryProducts(products.stream().map(CategoryProduct::of).toList())
                .build();
    }

    public CategoryPriceRangeResponse findCategoryPriceRange(String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new MusinsaException(ErrorCode.CATEGORY_NOT_FOUND));
        
        List<BrandPriceDto> lowestPrices = productRepository.findLowestPriceByCategory(category.getId());
        List<BrandPriceDto> highestPrices = productRepository.findHighestPriceByCategory(category.getId());
        
        return new CategoryPriceRangeResponse(categoryName, lowestPrices, highestPrices);
    }
}
