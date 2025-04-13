# OTP Service

## 📌 Описание

Простой и безопасный backend-сервис для подтверждения операций с помощью временных OTP-кодов. Поддерживает рассылку кодов через Email, SMS (эмулятор), Telegram, а также сохранение в файл.

## ✅ Основной функционал

- 🔐 Регистрация и логин пользователей с JWT-аутентификацией
- 👤 Поддержка ролей: `USER`, `ADMIN`
- 🔧 Админ может:
  - Изменять конфигурацию OTP (длина и срок действия)
  - Получать список пользователей (без администраторов)
  - Удалять пользователей и связанные с ними OTP-коды
- 📩 Пользователь может:
  - Сгенерировать OTP-код
  - Получить его через Email / SMS / Telegram / файл
  - Проверить код на валидность
- ⏱️ Планировщик автоматически переводит просроченные коды в статус `EXPIRED`
- 📊 Логирование всех операций через AOP
- 🌐 Swagger UI для тестирования

## 📁 Структура проекта

```
features/
  auth/          # Регистрация, логин, JWT
  otp/           # Генерация, валидация, хранение OTP-кодов
  otp_config/    # Конфигурация OTP
  user/          # Управление пользователями
  delivery/      # Каналы доставки (email, sms, telegram, file)
common/          # Общие утилиты и аспект логирования
db.migration/    # Flyway миграции базы данных
```

## ⚙️ Настройка

- Java 23
- PostgreSQL 17
- Gradle 8.13
- SMPP эмулятор (для тестирования SMS)
- Telegram Bot Token (для работы с Telegram)
- Flyway (для миграций базы данных)

### 📦 Сборка и запуск

1. Убедитесь, что PostgreSQL 17 запущен, впишите его пареметры в `application.yml` (как в шаблоне `application-template.yml`).

   🔧 Дополнительные параметры:
   - **Email (SMTP)** — используйте SMTP-сервер с поддержкой TLS. Пример для Gmail:
     ```yaml
     spring:
       mail:
         host: smtp.gmail.com
         port: 587
         username: your_email@gmail.com
         password: your_app_password
         properties:
           mail.smtp.auth: true
           mail.smtp.starttls.enable: true
     email:
       from: your_email@gmail.com
     ```
     ❗ Используйте пароль приложения, если включена двухфакторная аутентификация.

   - **Telegram** — создайте бота с помощью [@BotFather](https://t.me/BotFather), получите токен и пропишите:
     ```yaml
     telegram:
       token: 123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11
     ```

   - **SMPP** — подключите эмулятор (например, [SMPPSim](https://github.com/opensmpp/smppsim)) и настройте параметры:
     ```yaml
     smpp:
       host: localhost
       port: 2775
       systemId: smppclient1
       password: password
       systemType: OTP
       sourceAddr: OTPService
     ```

2. Убедитесь, что база данных `otp_service` существует.
3. Выполните миграции с помощью Flyway (происходит автоматически при запуске).
4. Соберите проект:
   ```bash
   ./gradlew build
   ```
5. Запустите приложение:
   ```bash
   ./gradlew bootRun
   ```

## Документация API

Полная документация доступна в **Swagger UI**:

- **URL**: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

Либо в документе:

- **Файл**: [docs/openapi-spec.json](docs/openapi-spec.json)

---

**Автор:** @abigotado

Этот проект доступен под лицензией [MIT](LICENSE).

## 📬 Примеры API-запросов

### Регистрация пользователя
```
POST `/api/auth/register`
```json
{
  "username": "user1",
  "password": "password"
}
```

### Логин пользователя
```
POST `/api/auth/login`
```json
{
  "username": "user1",
  "password": "password"
}
```

Ответ:
```json
{
  "token": "..."
}
```

### Генерация OTP-кода

POST `/api/otp/generate`
```json
{
  "operationId": "123e4567-e89b-12d3-a456-426614174000",
  "channel": "EMAIL",
  "recipient": "user@example.com"
}
```

### Проверка OTP-кода

POST `/api/otp/validate`
```json
{
  "operationId": "123e4567-e89b-12d3-a456-426614174000",
  "code": "456789"
}
```

### Получение пользователей (админ)

GET `/api/admin/users`
```
Authorization: Bearer <token>
```

### Удаление пользователя (админ)

DELETE `/api/admin/users/{userId}`
```
Authorization: Bearer <token>
```

### Изменение конфигурации OTP (админ)

PATCH `/api/admin/config`
```
Authorization: Bearer <token>
```

```json
{
  "codeLength": 6,
  "ttlSeconds": 120
}
```
