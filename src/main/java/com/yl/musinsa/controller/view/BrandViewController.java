package com.yl.musinsa.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 브랜드 관리 페이지 뷰 컨트롤러 (Thymeleaf 뷰 반환)
 */
@Controller
@RequestMapping("/admin/brands")
public class BrandViewController {
    
    /**
     * 브랜드 관리 페이지
     */
    @GetMapping("/register")
    public String registerPage() {
        return "admin/brand-register";
    }
}
