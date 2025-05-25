package com.yl.musinsa.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 전역 예외 처리 핸들러
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MusinsaException.class)
    public String handleMusinsaException(MusinsaException e, 
                                       RedirectAttributes redirectAttributes,
                                       HttpServletRequest request) {
        
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        
        // 이전 페이지 있다면 해당 URL로 리다이렉트
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        }
        
        return "redirect:/";
    }
    
    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException e,
                                    RedirectAttributes redirectAttributes,
                                    HttpServletRequest request) {
        
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        redirectAttributes.addFlashAttribute("error", errorMessage);
        
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        }
        
        return "redirect:/";
    }
}
