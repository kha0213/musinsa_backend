package com.yl.musinsa.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * MusinsaException 전역 처리
     * redirectAttributes에 "error" 키로 메시지 저장 후 이전 페이지로 리다이렉트
     */
    @ExceptionHandler(MusinsaException.class)
    public String handleMusinsaException(MusinsaException e, 
                                       RedirectAttributes redirectAttributes,
                                       HttpServletRequest request) {
        
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        }
        
        return "redirect:/";
    }
}
