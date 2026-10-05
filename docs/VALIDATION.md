# Kiểm tra gói mã nguồn

- 75 file Java.
- 21 JPA Entity.
- 21 Spring Data JPA Repository.
- 25 template Thymeleaf (user/admin/fragments).
- Dữ liệu seed MySQL được chuyển từ SQLite gốc: 358 lệnh insert.
- Không chứa file `.py` hoặc `.db` trong sản phẩm Spring Boot cuối.
- Mã Java đã được kiểm tra cú pháp và liên kết nội bộ bằng biên dịch tĩnh với API stubs cho các dependency framework.
- Các template HTML đã được parse kiểm tra cấu trúc cơ bản.

Để xác nhận build/running thực tế trên máy đích, Maven cần tải dependency Spring Boot và cần một MySQL server khả dụng theo `application.properties`.
