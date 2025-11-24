# ⚡ WattWise — Modern Electricity Billing System

WattWise is a robust, Spring Boot-based electricity billing application designed for efficiency and ease of use. It features a modern, responsive UI and integrates seamlessly with **Supabase (PostgreSQL)** for reliable data management.

## 🚀 Key Features

### 🎨 Modern User Experience
- **Fresh UI Overhaul**: A professional "Deep Slate & Sky Blue" design system.
- **Responsive Layout**: Fully optimized for desktop and mobile devices.
- **Interactive Dashboard**: Real-time stats, quick actions, and summary cards.

### 🛠️ Core Functionality
- **Customer Management**: Create and manage customer profiles with **auto-generated Meter Numbers** (e.g., `MTR-2024-0001`).
- **Tariff Slabs**: Define flexible billing rates and fixed charges based on consumption.
- **Meter Readings**: Record readings with automatic bill calculation logic.
- **Billing & Payments**: Generate bills and track payment status.

### 🔒 Security & Architecture
- **Role-Based Access**: Secure Admin, Clerk, and Customer roles.
- **Supabase Integration**: High-performance PostgreSQL database with Flyway migrations.
- **Audit Logging**: Database triggers track sensitive changes automatically.

---

## 🛠️ Tech Stack

- **Backend**: Java 17, Spring Boot 3.3
- **Database**: PostgreSQL (Supabase), Flyway
- **Frontend**: Thymeleaf, Bootstrap 5, Custom CSS
- **Security**: Spring Security 6

---

## ⚙️ Setup & Configuration

### 1. Prerequisites
- Java 17+
- Maven 3.8+
- A [Supabase](https://supabase.com/) project

### 2. Database Configuration
Update `src/main/resources/application.properties` with your Supabase credentials:

```properties
spring.datasource.url=jdbc:postgresql://<YOUR_SUPABASE_HOST>:5432/postgres?sslmode=require
spring.datasource.username=<YOUR_DB_USER>
spring.datasource.password=<YOUR_DB_PASSWORD>
```

### 3. Run the Application
```bash
mvn clean spring-boot:run
```

- **App URL**: [http://localhost:8082](http://localhost:8082)
- **Default Admin**: `admin` / `admin123` (or as configured in `Bootstrap.java`)

---

## 📂 Database Schema

The project uses **Flyway** for database migrations.
- **V1__init_supabase.sql**: Sets up tables (`customers`, `bills`, `payments`), sequences (`meter_no_seq`), and triggers for automation.

---

## 📝 License
This project is open-source and available under the MIT License.

