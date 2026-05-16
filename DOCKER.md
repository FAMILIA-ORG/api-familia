# Docker Setup for Familia

## Quick Start

### Build and start all services:
```bash
docker-compose up --build
```

### Access the application:
- **Backend API**: http://localhost:8080
- **Mailpit Web UI**: http://localhost:8025
- **MySQL**: localhost:3306

### Useful Commands

**Stop services:**
```bash
docker-compose down
```

**Stop and remove volumes (clean slate):**
```bash
docker-compose down -v
```

**View logs:**
```bash
docker-compose logs -f app
```

**Access MySQL:**
```bash
docker-compose exec mysql mysql -uroot -proot db_familia
```

**Rebuild only the app:**
```bash
docker-compose up --build app
```

## Services Included

- **app**: Spring Boot application (Java 21)
- **mysql**: MySQL 8.0 database
- **mailpit**: Email testing server (SMTP + Web UI)

## Environment Configuration

All environment variables are pre-configured in `docker-compose.yml`. To customize:
- Edit `docker-compose.yml` and modify the `environment` section under `app`
- Rebuild with: `docker-compose up --build app`

## Production Considerations

For production deployment:
1. Use a `.env` file for sensitive data
2. Remove `volumes` section for avatars (use cloud storage)
3. Increase MySQL memory constraints
4. Use secrets management for JWT secret
5. Add ingress/reverse proxy (nginx)
