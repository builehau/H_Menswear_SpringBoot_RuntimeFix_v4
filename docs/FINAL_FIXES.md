# FINAL FIXES — H-Menswear Spring Boot

Nguồn giao diện chuẩn: `fasion_store(3).zip` (Flask/Jinja). Bản Spring chỉ thay backend/template engine, không chủ động thiết kế lại giao diện người dùng.

## Các lỗi đã xử lý

- `admin/customers`: sửa lỗi Thymeleaf template parsing.
- `admin/orders`: sửa lỗi Thymeleaf template parsing.
- `admin/order_detail`: sửa lỗi Thymeleaf template parsing và link chi tiết đơn từ Dashboard.
- `admin/products`: tồn kho cao/thấp, chi tiết theo size, cập nhật trạng thái, modal thêm/sửa theo giao diện gốc.
- Button/link: bỏ underline không mong muốn, bổ sung hover/transition nhất quán.
- Dashboard: nút kiểm tra kho + giao dịch gần nhất.
- Reports: giữ lại bố cục gốc và dữ liệu báo cáo vận hành.
- Revenue: in báo cáo, xuất CSV, biểu đồ cơ cấu dòng tiền.
- Audit logs: đăng nhập/đăng xuất của nhân viên + khách hàng, ngoài các thao tác quản trị.
- Employees: trạng thái tài khoản đăng nhập `Đã cấp / Chưa có`.
- Manufacturers: số `SP Đang Cung Cấp` + xem danh sách sản phẩm.
- Home/Collections/Products/Product Detail: khôi phục theo template gốc.
- Cart: tồn kho theo size + cảnh báo `Size ... chỉ còn ... SP!`.
- Order detail: hiển thị mã sản phẩm `SPxxx` thay cho ID nội bộ.
- Thêm trang lỗi H-Menswear thay Whitelabel mặc định.

## Stack cuối

- Spring Boot 3.4.5
- Spring Security
- Spring Data JPA / Hibernate
- Thymeleaf
- MySQL/MariaDB (XAMPP tương thích)
- Maven

## Kiểm tra tĩnh cuối

- 26 template: không còn Jinja syntax.
- HTML/Thymeleaf attribute balance: PASS.
- JavaScript inline (`node --check`): PASS.
- Java syntax-only scan: PASS.
- Đối chiếu route nội bộ với Spring controllers.

> Kiểm tra runtime cuối cần dùng chính MySQL/MariaDB trên máy chạy ứng dụng: `mvn clean spring-boot:run`.
