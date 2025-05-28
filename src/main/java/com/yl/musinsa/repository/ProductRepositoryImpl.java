package com.yl.musinsa.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    @Override
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

    @Override
    public Brand findLowestBrand() {
        return queryFactory
                .selectFrom(brand)
                .join(product.category, category)
                .join(product.brand, brand)
                .groupBy(product.brand.id)
                .orderBy(product.price.sum().asc())
                .limit(1)
                .fetchOne();
    }
}
