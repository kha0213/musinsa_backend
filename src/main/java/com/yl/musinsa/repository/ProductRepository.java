package com.yl.musinsa.repository;

import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByBrandInOrderByBrand_NameAsc(Collection<Brand> brands);

}
