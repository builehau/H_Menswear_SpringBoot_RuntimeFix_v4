package com.hmenswear.fashionstore.controller;

import com.hmenswear.fashionstore.security.StoreUserPrincipal;
import com.hmenswear.fashionstore.service.CurrentUserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAdvice {
    private final CurrentUserService currentUserService;

    public GlobalModelAdvice(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    @ModelAttribute
    public void addCurrentUser(Model model) {
        model.addAttribute("isCustomer", false);
        model.addAttribute("isEmployee", false);
        model.addAttribute("isManager", false);
        model.addAttribute("currentUserName", "");
        model.addAttribute("currentUserRole", "");
        model.addAttribute("currentUserType", "GUEST");

        currentUserService.principal().ifPresent(p -> {
            boolean customer = p.getType() == StoreUserPrincipal.Type.CUSTOMER;
            boolean employee = p.getType() == StoreUserPrincipal.Type.EMPLOYEE;
            model.addAttribute("currentUserName", p.getDisplayName());
            model.addAttribute("currentUserRole", p.getRoleLabel());
            model.addAttribute("currentUserType", p.getType().name());
            model.addAttribute("isCustomer", customer);
            model.addAttribute("isEmployee", employee);
            model.addAttribute("isManager", employee && "Quản lý".equals(p.getRoleLabel()));
        });
    }
}
