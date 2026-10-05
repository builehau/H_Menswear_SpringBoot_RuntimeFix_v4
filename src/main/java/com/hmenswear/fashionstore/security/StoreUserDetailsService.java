package com.hmenswear.fashionstore.security;

import com.hmenswear.fashionstore.entity.*;
import com.hmenswear.fashionstore.repository.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StoreUserDetailsService implements UserDetailsService {
    private final EmployeeLoginRepository employeeLoginRepository;
    private final CustomerRepository customerRepository;

    public StoreUserDetailsService(EmployeeLoginRepository employeeLoginRepository, CustomerRepository customerRepository) {
        this.employeeLoginRepository = employeeLoginRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var empLogin = employeeLoginRepository.findFirstByEmailIgnoreCaseOrderByIdDesc(email);
        if (empLogin.isPresent()) {
            EmployeeLogin login = empLogin.get();
            Employee e = login.getEmployee();
            if (e == null) throw new UsernameNotFoundException("Tài khoản nhân viên không còn liên kết hồ sơ");
            String label = e.getPermission() == null ? "Nhân viên" : e.getPermission().getChucVu();
            String role = switch (label) {
                case "Quản lý" -> "ROLE_MANAGER";
                case "NV xác nhận đơn" -> "ROLE_ORDER_STAFF";
                case "NV Kho" -> "ROLE_WAREHOUSE_STAFF";
                default -> "ROLE_EMPLOYEE";
            };
            return new StoreUserPrincipal(e.getId(), login.getEmail(), login.getPassword(), e.getTenNv(), label,
                    StoreUserPrincipal.Type.EMPLOYEE, List.of(new SimpleGrantedAuthority(role)), true);
        }
        Customer c = customerRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản"));
        boolean enabled = !"Bị khóa".equalsIgnoreCase(c.getTrangThai());
        return new StoreUserPrincipal(c.getId(), c.getEmail(), c.getPassword(), c.getTenKh(), c.getLoaiKh(),
                StoreUserPrincipal.Type.CUSTOMER, List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER")), enabled);
    }
}
