package com.yl.musinsa.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yl.musinsa.dto.BrandPriceDto;
import com.yl.musinsa.dto.LowPriceByCategoryDto;
import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static com.yl.musinsa.entity.QBrand.brand;
import static com.yl.musinsa.entity.QCategory.category;
import static com.yl.musinsa.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * 각 카테고리별 최소 금액 가진 브랜드의 상품을 찾는 메서드
     */
    public List<LowPriceByCategoryDto> findByLowestPriceCategory() {
        QProduct subProduct = new QProduct("subProduct");
        JPQLQuery<BigDecimal> minPriceSubQuery = JPAExpressions
                .select(subProduct.price.min())
                .from(subProduct)
                .where(subProduct.category.id.eq(product.category.id));

        return queryFactory
                .select(Projections.bean(LowPriceByCategoryDto.class,
                        product.category.id.as("categoryId"),
                        product.category.name.as("categoryName"),
                        product.brand.id.as("brandId"),
                        product.brand.name.as("brandName"),
                        product.id.as("productId"),
                        product.price
                ))
                .from(product)
                .join(product.category, category)
                .join(product.brand, brand)
                .where(product.price.eq(minPriceSubQuery))
                .fetch();
    }

    /**
     * 단일 브랜드로 모든 카테고리 구매 시 총합이 가장 저렴한 브랜드 찾기
     */
    public Brand findLowestBrand() {
        Long brandId = queryFactory
                .select(product.brand.id)
                .from(product)
                .groupBy(product.brand.id)
                .orderBy(product.price.sum().asc())
                .limit(1)
                .fetchOne();

        return queryFactory
                .selectFrom(brand)
                .where(brand.id.eq(brandId))
                .fetchOne();
    }

    /**
     * 특정 카테고리에서 최저가격 브랜드들 조회
     */
    public List<BrandPriceDto> findLowestPriceByCategory(Long categoryId) {
        BigDecimal minPrice = queryFactory
                .select(product.price.min())
                .from(product)
                .where(product.category.id.eq(categoryId))
                .fetchOne();

        return queryFactory
                .select(Projections.constructor(BrandPriceDto.class,
                        product.brand.name,
                        product.price
                ))
                .from(product)
                .join(product.brand, brand)
                .where(product.category.id.eq(categoryId)
                        .and(product.price.eq(minPrice)))
                .fetch();
    }

    /**
     * 특정 카테고리에서 최고가격 브랜드들 조회
     */
    public List<BrandPriceDto> findHighestPriceByCategory(Long categoryId) {
        BigDecimal maxPrice = queryFactory
                .select(product.price.max())
                .from(product)
                .where(product.category.id.eq(categoryId))
                .fetchOne();

        return queryFactory
                .select(Projections.constructor(BrandPriceDto.class,
                        product.brand.name,
                        product.price
                ))
                .from(product)
                .join(product.brand, brand)
                .where(product.category.id.eq(categoryId)
                        .and(product.price.eq(maxPrice)))
                .fetch();
    }
}
