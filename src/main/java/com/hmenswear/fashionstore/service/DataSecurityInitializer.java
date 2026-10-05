package com.hmenswear.fashionstore.service;

import com.hmenswear.fashionstore.entity.EmployeeLogin;
import com.hmenswear.fashionstore.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Chuẩn hoá dữ liệu bảo mật khi chạy với database được giữ lại từ các bản cũ.
 * Việc này không xoá nghiệp vụ; chỉ mã hoá mật khẩu plain-text và loại bản ghi
 * đăng nhập nhân viên bị trùng theo cùng một nhân viên (giữ bản mới nhất).
 */
@Component
public class DataSecurityInitializer implements CommandLineRunner {
    private final CustomerRepository customers;
    private final EmployeeLoginRepository employeeLogins;
    private final PasswordEncoder encoder;

    public DataSecurityInitializer(CustomerRepository customers,
                                   EmployeeLoginRepository employeeLogins,
                                   PasswordEncoder encoder) {
        this.customers = customers;
        this.employeeLogins = employeeLogins;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        customers.findAll().forEach(c -> {
            if (c.getPassword() != null && !c.getPassword().isBlank() && !c.getPassword().startsWith("$2")) {
                c.setPassword(encoder.encode(c.getPassword()));
                customers.save(c);
            }
        });

        // Các bản Spring cũ có thể từng tạo nhiều employee_login cho cùng một nhân viên.
        // Giữ bản ghi có id lớn nhất để các trang nhân viên/đăng nhập không bị NonUniqueResultException.
        Map<Long, List<EmployeeLogin>> byEmployee = new LinkedHashMap<>();
        for (EmployeeLogin login : employeeLogins.findAll()) {
            Long employeeId = login.getEmployee() == null ? null : login.getEmployee().getId();
            if (employeeId != null) byEmployee.computeIfAbsent(employeeId, k -> new ArrayList<>()).add(login);
        }
        for (List<EmployeeLogin> group : byEmployee.values()) {
            group.sort(Comparator.comparing(EmployeeLogin::getId, Comparator.nullsFirst(Long::compareTo)).reversed());
            if (group.size() > 1) employeeLogins.deleteAll(group.subList(1, group.size()));
        }

        employeeLogins.findAll().forEach(e -> {
            if (e.getPassword() != null && !e.getPassword().isBlank() && !e.getPassword().startsWith("$2")) {
                e.setPassword(encoder.encode(e.getPassword()));
                employeeLogins.save(e);
            }
        });
    }
}
