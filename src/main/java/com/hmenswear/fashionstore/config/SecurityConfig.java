package com.hmenswear.fashionstore.config;

import com.hmenswear.fashionstore.security.StoreUserPrincipal;
import com.hmenswear.fashionstore.service.AuditService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final AuditService audit;
    public SecurityConfig(AuditService audit){this.audit=audit;}

    @Bean public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth->auth
                .requestMatchers("/", "/home", "/products/**", "/product/**", "/collections", "/contact", "/vouchers", "/login", "/register", "/css/**", "/js/**", "/images/**", "/uploads/**", "/error", "/api/apply-voucher").permitAll()
                .requestMatchers("/admin", "/admin/", "/admin/dashboard").hasAnyRole("MANAGER","ORDER_STAFF","WAREHOUSE_STAFF")
                .requestMatchers("/admin/products/**", "/admin/manufacturers/**", "/admin/api/product/**", "/admin/api/manufacturer/**").hasAnyRole("MANAGER","WAREHOUSE_STAFF")
                .requestMatchers("/admin/orders/**", "/admin/order/**", "/admin/api/order/**", "/admin/customers/**", "/admin/api/customer/**").hasAnyRole("MANAGER","ORDER_STAFF")
                .requestMatchers("/admin/**").hasRole("MANAGER")
                .requestMatchers("/cart/**", "/checkout/**", "/profile/**", "/favorites", "/api/cart/**", "/api/checkout/**", "/api/profile/**", "/api/favorite/**", "/api/notification/**", "/api/order/cancel", "/api/voucher/save").hasRole("CUSTOMER")
                .anyRequest().authenticated())
            .formLogin(form->form.loginPage("/login").loginProcessingUrl("/login").usernameParameter("email").passwordParameter("password")
                    .successHandler(successHandler()).failureUrl("/login?error=true").permitAll())
            .logout(logout->logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
                    .logoutSuccessHandler(logoutSuccessHandler()).invalidateHttpSession(true).clearAuthentication(true).permitAll())
            .csrf(csrf->csrf.ignoringRequestMatchers("/api/**","/admin/api/**"));
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return (request,response,authentication)->{
            Object principal=authentication.getPrincipal();
            if(principal instanceof StoreUserPrincipal p){
                try{audit.logPrincipal(p,"Đăng nhập hệ thống","Đăng nhập thành công từ "+request.getRemoteAddr());}catch(Exception ignored){}
                if(p.getType()== StoreUserPrincipal.Type.EMPLOYEE){response.sendRedirect("/admin/dashboard");return;}
            }
            String next=request.getParameter("next");
            if(next!=null&&next.startsWith("/")){response.sendRedirect(next);return;}
            SavedRequest saved=new HttpSessionRequestCache().getRequest(request,response);
            if(saved!=null&&saved.getRedirectUrl()!=null){response.sendRedirect(saved.getRedirectUrl());return;}
            response.sendRedirect("/home");
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return (request,response,authentication)->{
            if(authentication!=null&&authentication.getPrincipal() instanceof StoreUserPrincipal p){
                try{audit.logPrincipal(p,"Đăng xuất hệ thống","Đăng xuất khỏi hệ thống từ "+request.getRemoteAddr());}catch(Exception ignored){}
            }
            response.sendRedirect("/login?logout=true");
        };
    }
}
