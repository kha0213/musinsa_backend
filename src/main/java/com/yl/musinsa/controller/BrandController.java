package com.yl.musinsa.controller;

import com.yl.musinsa.entity.Brand;
import com.yl.musinsa.repository.BrandRepository;
import com.yl.musinsa.service.BrandService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;
    private final BrandRepository brandRepository;
    
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("brands", brandRepository.findAll());
        return "admin/brand-register";
    }
    
    @PostMapping("/register")
    public String registerBrand(@Valid @NotBlank @RequestParam String name,
                               @RequestParam(required = false) String description,
                               RedirectAttributes redirectAttributes) {
        
        Brand brand = brandService.registerBrand(name, description);
        redirectAttributes.addFlashAttribute("message",
                String.format("브랜드 [%s]가 성공적으로 등록되었습니다.", brand.getName()));

        return "redirect:/admin/brands/register";
    }
}
