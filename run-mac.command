#!/bin/bash
set -u
cd "$(dirname "$0")"

echo "========================================"
echo " H-Menswear - Spring Boot / MySQL"
echo "========================================"

if ! command -v java >/dev/null 2>&1; then
  echo "❌ Chưa tìm thấy Java. Cần JDK 17 trở lên."
  read -r -p "Nhấn Enter để đóng..."
  exit 1
fi

# Hỗ trợ đúng cách cài Maven thủ công đã dùng trên macOS 12.
if ! command -v mvn >/dev/null 2>&1 && [ -x "$HOME/tools/apache-maven/bin/mvn" ]; then
  export MAVEN_HOME="$HOME/tools/apache-maven"
  export PATH="$MAVEN_HOME/bin:$PATH"
fi

if ! command -v mvn >/dev/null 2>&1; then
  echo "❌ Chưa tìm thấy Maven."
  echo "   Nếu đã giải nén Maven: $HOME/tools/apache-maven/bin/mvn"
  read -r -p "Nhấn Enter để đóng..."
  exit 1
fi

DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-3306}"
if command -v nc >/dev/null 2>&1; then
  if ! nc -z "$DB_HOST" "$DB_PORT" >/dev/null 2>&1; then
    echo "⚠️  Chưa thấy MySQL/MariaDB tại $DB_HOST:$DB_PORT."
    echo "   Hãy mở XAMPP và Start MySQL trước khi tiếp tục."
    read -r -p "Nhấn Enter sau khi đã bật MySQL..."
  fi
fi

echo "Java : $(java -version 2>&1 | head -n 1)"
echo "Maven: $(mvn -version 2>/dev/null | head -n 1)"
echo "DB   : ${DB_URL:-jdbc:mysql://localhost:3306/fashion_store}"
echo ""
echo "▶ Đang khởi động. Khi thấy 'Started FashionStoreApplication', mở:"
echo "  http://localhost:8080"
echo ""
exec mvn spring-boot:run
