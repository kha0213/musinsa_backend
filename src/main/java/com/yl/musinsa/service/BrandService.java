package com.yl.musinsa.service;

import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.exception.ErrorCode;
import com.yl.musinsa.exception.MusinsaException;
import com.yl.musinsa.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BrandService {
    
    private final BrandRepository brandRepository;
    
    @Transactional
    public Brand saveBrand(String name, String description) {
        if (brandRepository.existsByName(name)) {
            throw new MusinsaException(ErrorCode.DUPLICATE_NAME);
        }
        
        Brand brand = Brand.create(name, description);
        return brandRepository.save(brand);
    }
    
    /**
     * 모든 브랜드 조회
     */
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
}
