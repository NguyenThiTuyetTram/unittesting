Unit Testing Project - Spring Boot


 Cấu trúc thư mục

unittesting/
│-- src/
│   ├── main/
│   │   ├── java/com/example/unittesting/
│   │   │   ├── entity/          # Chứa class Product
│   │   │   ├── repository/      # Chứa ProductRepository
│   │   │   ├── service/         # Chứa ProductService
│   │   │   ├── controller/      # Chứa ProductController
│   ├── test/                    # Chứa unit test
│-- pom.xml                      # Cấu hình dependencies
│-- README.md                    # Hướng dẫn sử dụng
```

 Cài đặt
 Yêu cầu hệ thống
- **Java JDK 17** → [Tải Java JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- **Maven** → [Tải Maven](https://maven.apache.org/download.cgi)
- **Git** → [Tải Git](https://git-scm.com/downloads)

 Clone repo từ GitHub
```sh
git clone https://github.com/NguyenThiTuyetTram/unittesting.git
cd unittesting
```

 Cấu hình database (nếu cần)
Mặc định project sử dụng H2 Database. Nếu muốn dùng MySQL, sửa `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/unittesting
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
👉 **Tạo database MySQL:**
```sql
CREATE DATABASE unittesting;
```

 Chạy project
```sh
mvn clean package
mvn spring-boot:run
```
🔥 **Sau khi chạy, API sẽ hoạt động tại:** `http://localhost:8080/`

 Kiểm tra API
 Lấy danh sách sản phẩm
```sh
curl -X GET http://localhost:8080/api/products
```
 Thêm sản phẩm mới
```sh
curl -X POST http://localhost:8080/api/products -H "Content-Type: application/json" -d '{"name":"Laptop Dell","price":1500}'
```

## 🧪 Chạy Unit Test
```sh
mvn test
```




