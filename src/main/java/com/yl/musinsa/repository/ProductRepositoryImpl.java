package com.yl.musinsa.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yl.musinsa.dto.LowPriceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.yl.musinsa.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * 각 카테고리별 최소 금액 가진 브랜드의 상품을 찾는 메서드
     */
    public List<LowPriceDto> findByLowPriceCategoryBrand() {
        JPQLQuery<Tuple> subQuery = JPAExpressions
                .select(product.category, product.price.min())
                .groupBy(product.category);

        return queryFactory
                .select(Projections.bean(LowPriceDto.class,
                        product.category.id.as("categoryId"),
                        product.category.name.as("categoryName"),
                        product.brand.id.as("brandId"),
                        product.brand.name.as("brandName"),
                        product.id.as("productId"),
                        product.price
                ))
                .from(product)
                .where(
                    Expressions.list(product.category, product.price)
                        .in(subQuery)
                )
                .fetch();
    }
}
