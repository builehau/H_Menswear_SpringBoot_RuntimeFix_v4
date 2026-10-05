# H-Menswear — Fashion Store Management

## RuntimeFix v3 – Báo cáo & Doanh thu

Bản này sửa hai trang `/admin/reports` và `/admin/revenue`:

- bỏ phụ thuộc Chart.js CDN và vẽ biểu đồ trực tiếp bằng Canvas API, nên biểu đồ vẫn chạy khi CDN bị chặn;
- sửa lỗi Thymeleaf ở nút phản hồi khách hàng bằng `data-*` thay vì chèn chuỗi trực tiếp vào `onclick`;
- sửa thao tác trả lời phản hồi và tương thích cả `message` / `reply_content`;
- hiển thị tỷ lệ hoàn tất đơn và số phản hồi chờ xử lý từ dữ liệu thực;
- sửa bộ lọc doanh thu theo ngày/năm, in báo cáo và xuất CSV UTF-8;
- giữ nguyên giao diện H-Menswear và các công nghệ Spring Boot / Spring Security / Spring Data JPA / Thymeleaf / MySQL.


**Đề tài:** Xây dựng hệ thống Web quản lý vận hành và kinh doanh cửa hàng thời trang sử dụng **Spring Boot, Spring Security, Spring Data JPA, Thymeleaf và MySQL**.

Đây là phiên bản chuyển đổi từ sản phẩm Flask/SQLite `fasion_store(3).zip` sang đúng stack Java của đề tài mở. Cấu trúc nghiệp vụ, dữ liệu mẫu, luồng người dùng/admin và phong cách giao diện H-Menswear được giữ lại; backend được viết lại theo kiến trúc Spring Boot.

## 1. Công nghệ

- Java 17
- Spring Boot 3.4.5
- Spring MVC
- Spring Security + BCrypt + phân quyền theo vai trò
- Spring Data JPA / Hibernate
- Thymeleaf + Thymeleaf Spring Security Extras
- MySQL 8.x
- Maven
- Chart.js cho dashboard/báo cáo
- ZXing cho barcode

## 2. Các phân hệ đã có

### Khách hàng
- Trang chủ, bộ sưu tập, danh sách sản phẩm.
- Tìm kiếm, lọc theo danh mục/thương hiệu/màu, sắp xếp, phân trang.
- Chi tiết sản phẩm, ảnh phụ, chọn size, tồn kho theo size, sản phẩm liên quan.
- Đăng ký, đăng nhập, đăng xuất.
- Giỏ hàng: thêm/cập nhật/xóa, kiểm tra tồn kho theo size.
- Mua ngay và checkout; COD/chuyển khoản.
- Voucher/khuyến mãi, lưu voucher.
- Sản phẩm yêu thích.
- Hồ sơ cá nhân, cập nhật thông tin, đổi mật khẩu.
- Lịch sử đơn hàng, yêu cầu hủy/hủy đơn.
- Thông báo và đánh dấu đã đọc.
- Liên hệ/phản hồi.

### Quản trị/Vận hành
- Dashboard KPI, doanh thu gần nhất, đơn mới, cảnh báo tồn kho.
- Quản lý sản phẩm: CRUD, ảnh, size/tồn kho, mã vạch, bật/tắt kinh doanh.
- Quản lý nhà sản xuất/thương hiệu.
- Quản lý đơn hàng: tìm kiếm/lọc, chi tiết, trạng thái, lịch sử hủy/bom hàng.
- Quản lý khách hàng: tìm kiếm, VIP/thường, khóa/mở, lịch sử mua hàng, cảnh báo VIP lâu không mua.
- Quản lý nhân viên và vai trò.
- Quản lý voucher.
- Báo cáo vận hành và phản hồi khách hàng.
- Doanh thu, lợi nhuận, doanh thu theo danh mục, xu hướng tháng.
- Audit log.
- Barcode sản phẩm.

## 3. Phân quyền Spring Security

| Vai trò | Quyền chính |
|---|---|
| `ROLE_MANAGER` | Toàn bộ trang quản trị |
| `ROLE_ORDER_STAFF` | Dashboard, khách hàng, đơn hàng |
| `ROLE_WAREHOUSE_STAFF` | Dashboard, sản phẩm, nhà sản xuất/kho |
| `ROLE_CUSTOMER` | Giỏ hàng, checkout, hồ sơ, yêu thích, voucher cá nhân |

Mật khẩu dữ liệu cũ trong `data.sql` được tự động nâng cấp sang **BCrypt** khi ứng dụng khởi động lần đầu (`DataSecurityInitializer`).

## 4. Chuẩn bị MySQL

Có thể dùng MySQL cài trên máy hoặc Docker.

### Cách A — MySQL có sẵn

Tạo database:

```sql
CREATE DATABASE fashion_store
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

Mặc định ứng dụng dùng:

```text
DB_URL=jdbc:mysql://localhost:3306/fashion_store...
DB_USERNAME=root
DB_PASSWORD=(rỗng)
```

Nếu MySQL của bạn có mật khẩu, đặt biến môi trường trước khi chạy, ví dụ macOS/Linux:

```bash
export DB_USERNAME=root
export DB_PASSWORD='mat_khau_mysql'
```

### Cách B — Docker

```bash
docker compose up -d mysql
export DB_PASSWORD=root
```

## 5. Chạy ứng dụng

Yêu cầu: **JDK 17+** và **Maven 3.9+**.

```bash
mvn clean spring-boot:run
```

Mở trình duyệt:

- Website: `http://localhost:8080`
- Đăng nhập: `http://localhost:8080/login`
- Admin: sau khi nhân viên đăng nhập sẽ tự chuyển đến `/admin/dashboard`

Spring Data JPA tạo/cập nhật schema MySQL. `src/main/resources/data.sql` nạp dữ liệu mẫu được chuyển từ `store.db`; các lệnh `INSERT IGNORE` giúp không nhân đôi seed khi chạy lại.

## 6. Tài khoản thử nghiệm

| Loại | Email | Mật khẩu | Vai trò |
|---|---|---|---|
| Quản lý | `hau@gmail.com` | `hau456` | Quản lý |
| Nhân viên đơn | `mai@gmail.com` | `mai123` | NV xác nhận đơn |
| Nhân viên kho | `phong@gmail.com` | `phong123` | NV Kho |
| Khách hàng | `minhnguyen@gmail.com` | `minh123` | Khách hàng |
| Khách hàng | `hau.customer@gmail.com` | `123456` | Khách hàng |

> Sau lần khởi động đầu tiên, các mật khẩu seed trên được lưu trong MySQL dưới dạng BCrypt, nhưng vẫn đăng nhập bằng mật khẩu ở bảng trên.

## 7. Cấu trúc project

```text
fashion_store_springboot/
├── pom.xml
├── docker-compose.yml
├── database/
│   └── mysql_setup.sql
├── docs/
├── uploads/
│   └── barcodes/
└── src/main/
    ├── java/com/hmenswear/fashionstore/
    │   ├── config/
    │   ├── controller/
    │   │   └── admin/
    │   ├── entity/
    │   ├── repository/
    │   ├── security/
    │   └── service/
    └── resources/
        ├── application.properties
        ├── data.sql
        ├── static/
        └── templates/
            ├── admin/
            ├── fragments/
            └── user/
```

## 8. Những điểm đã sửa so với phiên bản Flask

- Flask Controller → Spring MVC Controller.
- Python Model/SQL trực tiếp → Entity + Repository + Service.
- SQLite → MySQL.
- Jinja → Thymeleaf.
- Decorator phân quyền → Spring Security URL authorization.
- Mật khẩu plain text → BCrypt.
- Hoàn thiện đăng ký tài khoản (bản Flask có `register(): pass`).
- Sửa quan hệ `invoice_details -> invoices` thay cho FK cũ lỗi `old_invoices`.
- Checkout không còn gán cứng `ma_nv = 1`; hệ thống chọn nhân viên xác nhận đơn.
- Tồn kho ưu tiên quản lý theo **sản phẩm + size** và cập nhật tổng tồn.
- Checkout/đơn hàng dùng transaction để tránh dữ liệu dở dang.
- API quản trị được khóa quyền theo vai trò.

## 9. Ghi chú dữ liệu

Dữ liệu mẫu từ SQLite cũ đã được chuyển vào `data.sql`, gồm khoảng 60 sản phẩm, 21 khách hàng, 5 nhân viên, 10 nhà sản xuất, 39 hóa đơn cùng chi tiết đơn, voucher, tồn kho, feedback, thông báo và audit log.

Trong dữ liệu cũ có khách hàng trùng mã/email với tài khoản nhân viên. Bản chuyển đổi đổi khách demo cuối thành `KH021` / `hau.customer@gmail.com` để phù hợp ràng buộc duy nhất và tránh xung đột đăng nhập.

## 10. Bản hoàn thiện giữ giao diện gốc — các lỗi đã xử lý

Bản này lấy trực tiếp `fasion_store(3).zip` (Flask) làm **chuẩn giao diện và luồng thao tác**, không lấy giao diện của bản Spring trung gian làm chuẩn. CSS/layout chính của Home, Collections, Products, Product Detail, Cart và các trang Admin được giữ từ bản gốc; chỉ chuyển phần biến động dữ liệu Jinja sang Thymeleaf.

Các điểm đã xử lý trong vòng sửa cuối:

- Sửa cú pháp Thymeleaf gây lỗi parse/HTTP 500 ở `admin/customers.html`, `admin/orders.html`, `admin/order_detail.html`.
- Thêm trang `/error` theo phong cách H-Menswear để không còn Whitelabel mặc định nếu có lỗi môi trường ngoài dự kiến; exception thật vẫn được ghi ra Terminal.
- Khôi phục menu Home gốc: **Sản Phẩm – Liên Hệ – Bộ Sưu Tập**, giỏ hàng/hồ sơ/đăng xuất và sidebar **DANH MỤC**.
- Khôi phục giao diện gốc của Collections, Products và Product Detail; fallback ảnh/mô tả xử lý cả giá trị rỗng như Jinja cũ.
- Giỏ hàng kiểm tra tồn theo **đúng size**, bao gồm cảnh báo dạng `Size M chỉ còn 2 SP!`.
- Admin Products có danh sách **TỒN KHO CAO / SẮP HẾT HÀNG**, chi tiết từng size, bật/tắt trạng thái và form thêm/sửa theo giao diện gốc.
- Dashboard có nút **Kiểm tra Kho**, giao dịch gần nhất và link đúng sang chi tiết đơn.
- Revenue có **In Báo Cáo**, **Xuất CSV** và biểu đồ **Cơ cấu Dòng tiền**.
- Audit Log ghi cả đăng nhập/đăng xuất của **nhân viên và khách hàng**; tương thích CSDL cũ bằng cách cho phép `audit_logs.ma_nv` rỗng với actor khách hàng/hệ thống.
- Employees hiển thị trạng thái tài khoản **Đã cấp / Chưa có** và cho phép cấp/cập nhật mật khẩu đăng nhập.
- Manufacturers có cột **SP Đang Cung Cấp** và xem danh sách sản phẩm theo nhà sản xuất.
- Chuẩn hoá anchor-button: bỏ gạch chân không mong muốn, giữ hiệu ứng hover/transition.
- Sửa mã sản phẩm trong chi tiết hóa đơn hiển thị đúng `SPxxx` thay vì ID nội bộ.
- Bổ sung kiểm tra trùng số điện thoại nhân viên khi thêm/sửa.

### Kiểm tra trước khi đóng gói

Đã thực hiện kiểm tra tĩnh trên toàn bộ template/source:

- Không còn Jinja (`{{ }}`, `{% %}`, `url_for`, `session.get`) trong Thymeleaf.
- Kiểm tra cân bằng biểu thức/thuộc tính Thymeleaf và các dấu `|...|` literal substitution.
- Parse HTML cơ bản cho toàn bộ template.
- `node --check` cho JavaScript inline sau khi thay các placeholder Thymeleaf.
- Kiểm tra cú pháp Java bằng `javac -proc:none` (không phát hiện lỗi cú pháp Java; việc resolve thư viện Spring cần Maven).
- Đối chiếu các endpoint `fetch()`/link nội bộ với Controller Spring.

Để xác nhận runtime trên máy dùng XAMPP/MySQL/MariaDB, bật MySQL trong XAMPP rồi chạy:

```bash
mvn clean spring-boot:run
```

Nếu Maven được cài thủ công ở `~/tools/apache-maven`, file `run-mac.command` trong project sẽ tự nhận Maven đó.

## Runtime fix 17/09/2026

Bản này sửa các lỗi runtime được phát hiện khi chạy thực tế bằng XAMPP/MySQL:

- Sửa cú pháp Thymeleaf ternary bị chuyển sai (`${condition} ? ...` -> `${condition ? ...}`) trên toàn bộ template. Đây là nguyên nhân chính làm nhiều trang Admin báo `template parsing`/Whitelabel và lỗi 200 bị chèn vào trang đang render.
- Viết lại phần dynamic của `/profile` theo đúng giao diện Flask gốc nhưng dùng biểu thức Thymeleaf an toàn; các tab Đơn hàng/Thông báo/Voucher/Yêu thích/Tài khoản hoạt động bằng JavaScript gốc.
- Chuẩn hoá dữ liệu view null-safe, đặc biệt đơn hàng, chi tiết đơn, khách hàng, nhân viên, NSX, voucher, tồn kho theo size.
- Sửa truy vấn tài khoản nhân viên để không lỗi khi database cũ có bản ghi `employee_login` trùng; khi khởi động sẽ giữ bản mới nhất của từng nhân viên.
- Sửa `/admin/customers`, `/admin/orders`, `/admin/order/{id}`, `/admin/manufacturers`, `/admin/employees` và các template có cùng lỗi cú pháp.
- Dashboard dùng dữ liệu ngày/tiền đã định dạng an toàn; Analytics/Order filter không còn lỗi với bản ghi cũ thiếu ngày, sản phẩm hoặc danh mục.
- Trang `/error` không còn hiển thị gây hiểu nhầm `200 OK` khi thực tế có exception trong quá trình render.

### Chạy sau khi thay bản mới

Bật MySQL trong XAMPP, sau đó đứng tại thư mục project và chạy:

```bash
mvn clean spring-boot:run
```

Không cần xoá database `fashion_store`; bản này có lớp tương thích để dùng tiếp dữ liệu của các bản trước.

## RuntimeFix v4 - Favorites & Order workflow
- Sửa nút Yêu thích ở trang chi tiết sản phẩm: toggle thêm/bỏ yêu thích và chuyển đăng nhập nếu chưa xác thực.
- Khách chỉ gửi yêu cầu hủy trước bước Xác nhận & Đóng gói.
- Từ trạng thái Đang xử lý/Đang giao/Hoàn tất: hệ thống từ chối hủy, tạo thông báo cho khách yêu cầu liên hệ Admin.
- Admin chấp nhận/từ chối yêu cầu hủy đều tạo thông báo cho khách.
- Trang Đơn hàng Admin đổi tab nghiệp vụ: Cần xử lý, Mới đặt, Đang đóng gói, Đang giao, Hoàn tất, Đã hủy; có bộ đếm từng nhóm.
