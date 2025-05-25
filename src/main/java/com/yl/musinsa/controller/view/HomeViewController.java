package com.yl.musinsa.controller.view;

import com.yl.musinsa.dto.BrandCategoryResponse;
import com.yl.musinsa.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 홈 화면 뷰 컨트롤러
 * 메인 페이지에서 모든 브랜드의 카테고리별 상품 조회
 */
@Controller
@RequiredArgsConstructor
public class HomeViewController {
    
    private final ProductService productService;
    
    /**
     * 홈 페이지 - 모든 브랜드의 카테고리별 상품 표시
     */
    @GetMapping("/")
    public String home(Model model) {
        List<BrandCategoryResponse> brandCategories = productService.findAllBrandCategory();
        model.addAttribute("brandCategories", brandCategories);
        return "home";
    }
}
