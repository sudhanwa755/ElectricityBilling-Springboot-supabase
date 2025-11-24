# Electricity Billing System — Spring Boot (Full Project)

## Run
```bash
mvn clean spring-boot:run
```
- App: http://localhost:8082
- Login: **sudhanwa / sudhanwa**
- H2 Console: http://localhost:8082/h2-console
  - JDBC URL: `jdbc:h2:mem:ebs`
  - User: `sa`
  - Password: *(blank)*

## Features
- Users: Login, Register new users.
- Customers: List + Add.
- Tariffs: List + Add.
- Readings: Add reading.
- Billing: Generate bill for a reading (`/billing/generate/{readingId}`).
- Flyway schema + Bootstrap seed data.
