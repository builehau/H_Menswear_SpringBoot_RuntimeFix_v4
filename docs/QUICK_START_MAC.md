# Chạy nhanh trên macOS

## 1. Kiểm tra Java

```bash
java -version
```

Cần JDK 17 trở lên.

## 2. Cài Maven nếu chưa có

Nếu có Homebrew:

```bash
brew install maven
```

## 3. MySQL

Tạo database `fashion_store` bằng MySQL Workbench hoặc chạy `database/mysql_setup.sql`.

Nếu tài khoản `root` có mật khẩu:

```bash
export DB_USERNAME=root
export DB_PASSWORD='MAT_KHAU_MYSQL'
```

## 4. Chạy

Cách nhanh nhất: double click file `run-mac.command`.

Hoặc Terminal:

```bash
mvn spring-boot:run
```

Sau đó mở: `http://localhost:8080`
