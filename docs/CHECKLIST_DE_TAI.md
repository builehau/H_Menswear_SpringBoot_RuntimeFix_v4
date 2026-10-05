# Checklist đáp ứng đề tài

**Đề tài:** Xây dựng hệ thống Web quản lý vận hành và kinh doanh cửa hàng thời trang sử dụng Spring Boot, Spring Security, Spring Data JPA, Thymeleaf và MySQL.

| Yêu cầu | Hiện thực trong project |
|---|---|
| Spring Boot | `FashionStoreApplication.java`, Maven Spring Boot parent/starter |
| Spring MVC | `controller/` và `controller/admin/` |
| Spring Security | `SecurityConfig`, `StoreUserDetailsService`, BCrypt, phân quyền 4 vai trò |
| Spring Data JPA | 21 Entity + 21 Repository + Service/`@Transactional` |
| Thymeleaf | `resources/templates/user`, `admin`, `fragments` |
| MySQL | MySQL Connector/J + datasource trong `application.properties` |
| Nghiệp vụ phức hợp | bán hàng, kho-size, đơn hàng, khách hàng, nhân viên, voucher, dashboard, doanh thu, báo cáo, audit |
| CRUD | sản phẩm, NSX, nhân viên, voucher; cập nhật khách/đơn |
| Phân quyền | Manager / Order Staff / Warehouse Staff / Customer |
| Bảo mật mật khẩu | BCrypt |
| Báo cáo thống kê | Dashboard, doanh thu/lợi nhuận, trạng thái đơn, hiệu suất nhân viên |
| CSDL quan hệ | sản phẩm-danh mục-NSX-kho; khách-giỏ-đơn; voucher; nhân viên-quyền; audit... |
