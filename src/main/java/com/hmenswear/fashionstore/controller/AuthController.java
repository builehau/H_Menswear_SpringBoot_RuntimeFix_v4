package com.hmenswear.fashionstore.controller;

import com.hmenswear.fashionstore.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    private final CustomerService customerService;
    public AuthController(CustomerService customerService){this.customerService=customerService;}
    @GetMapping("/login") public String login(@RequestParam(required=false) String next,@RequestParam(required=false) Boolean error,@RequestParam(required=false) Boolean logout,Model model){model.addAttribute("next",next);model.addAttribute("loginError",Boolean.TRUE.equals(error));model.addAttribute("logoutSuccess",Boolean.TRUE.equals(logout));return "user/login";}
    @GetMapping("/register") public String register(){return "user/register";}
    @PostMapping("/register") public String registerPost(@RequestParam String fullname,@RequestParam String email,@RequestParam String password,@RequestParam(name="confirm_password", required=false) String confirmPassword,RedirectAttributes ra){if(confirmPassword!=null&&!password.equals(confirmPassword)){ra.addFlashAttribute("errorMessage","Mật khẩu xác nhận không khớp");return "redirect:/register";}var r=customerService.register(fullname,email,password);if("success".equals(r.get("status"))){ra.addFlashAttribute("successMessage",r.get("message"));return "redirect:/login";}ra.addFlashAttribute("errorMessage",r.get("message"));return "redirect:/register";}
}
