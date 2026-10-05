package com.hmenswear.fashionstore.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Vá tương thích cho CSDL đã được tạo bởi các bản Spring trước.
 * Bản gốc bắt buộc audit_logs.ma_nv NOT NULL, trong khi phiên bản hoàn thiện
 * ghi cả đăng nhập/đăng xuất của khách hàng và tài khoản hệ thống.
 */
@Component
public class SchemaCompatibilityInitializer implements ApplicationRunner {
    private final JdbcTemplate jdbc;

    public SchemaCompatibilityInitializer(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            jdbc.execute("ALTER TABLE audit_logs MODIFY COLUMN ma_nv BIGINT NULL");
        } catch (Exception ignored) {
            // CSDL mới đã đúng schema hoặc DB engine tự xử lý nullable qua Hibernate.
        }
    }
}
