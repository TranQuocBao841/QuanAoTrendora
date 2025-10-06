# Sử dụng JDK 17 để build
FROM eclipse-temurin:17-jdk

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép toàn bộ code vào container
COPY . .

# Build ứng dụng (bỏ test để nhanh hơn)
RUN ./mvnw clean package -DskipTests

# Chạy file jar
CMD ["java", "-jar", "target/*.jar"]
