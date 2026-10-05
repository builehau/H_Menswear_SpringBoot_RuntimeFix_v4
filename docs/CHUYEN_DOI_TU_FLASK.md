# Ánh xạ từ sản phẩm Flask gốc sang Spring Boot

| Flask/SQLite cũ | Spring Boot/MySQL mới |
|---|---|
| `app.py` | `FashionStoreApplication.java` |
| `controllers/*.py` | `controller/*.java` |
| `models/*.py` | `entity` + `repository` + `service` |
| `utils/decorators.py` | `SecurityConfig` / Spring Security |
| Flask Session | `SecurityContext` / `UserDetails` |
| Jinja HTML | Thymeleaf HTML |
| `sqlite3` / `store.db` | Spring Data JPA + MySQL |
| SQL thủ công | Repository/JPQL + JPA |
| `database/seed_data.sql` | `resources/data.sql` cho MySQL |

## Các nghiệp vụ được giữ

- User: sản phẩm, lọc/tìm kiếm, collections, favorites, voucher, cart, checkout, profile, order history/cancel, notification, feedback.
- Admin: dashboard, products/stock/barcode, orders, customers, employees/roles, manufacturers, vouchers, reports, revenue/profit, audit logs.

## Cải tiến kỹ thuật

1. Password được BCrypt hóa.
2. Role được thực thi tập trung bằng Spring Security.
3. JPA mapping sửa quan hệ hóa đơn/chi tiết hóa đơn.
4. `@Transactional` cho luồng checkout và cập nhật kho.
5. Tồn kho theo size được kiểm tra trước khi thêm giỏ/đặt hàng.
6. Nhân viên xử lý đơn không còn gán cứng ID.
