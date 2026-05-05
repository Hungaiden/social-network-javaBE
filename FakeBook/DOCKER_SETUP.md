# FakeBook - Docker Setup Guide

## Prerequisites
- Docker & Docker Compose installed
- Java 21 (for local development)
- Maven 3.9+

## Quick Start

### 1. Clone cấu hình
```bash
cp .env.example .env
```

### 2. Build Docker Image
```bash
docker build -t fakebook:latest .
```

### 3. Chạy với Docker Compose
```bash
docker-compose up -d
```

### 4. Kiểm tra logs
```bash
docker-compose logs -f app
```

### 5. Truy cập ứng dụng
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html

## Các lệnh hữu ích

### View running containers
```bash
docker-compose ps
```

### Stop services
```bash
docker-compose down
```

### Remove volumes (xóa data)
```bash
docker-compose down -v
```

### Build lại image
```bash
docker-compose build --no-cache
```

### Truy cập PostgreSQL
```bash
docker-compose exec postgres psql -U postgres -d fakebook
```

### Truy cập Redis
```bash
docker-compose exec redis redis-cli
```

## Biến môi trường

Copy từ `.env.example` sang `.env` và chỉnh sửa các giá trị:

```env
DATABASE_NAME=fakebook
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=123456
SIGNER_KEY=your_secret_key
USERNAME_ADMIN=admin
PASSWORD_ADMIN=admin@123
EMAIL_ADMIN=admin@gmail.com
```

## Troubleshooting

### Port 5432 đã bị dùng
```bash
docker-compose down
# Hoặc thay đổi port trong docker-compose.yml
```

### App không kết nối được database
- Kiểm tra PostgreSQL healthy: `docker-compose logs postgres`
- Kiểm tra logs app: `docker-compose logs app`

### Rebuild từ đầu
```bash
docker-compose down -v
docker-compose build --no-cache
docker-compose up -d
```

## Production Deployment

Để production, sửa Dockerfile:

```dockerfile
# Thêm vào Stage 2 (runtime)
ENV SPRING_PROFILES_ACTIVE=production
```

Hoặc pass qua docker-compose.yml:

```yaml
environment:
  SPRING_PROFILES_ACTIVE: production
```

