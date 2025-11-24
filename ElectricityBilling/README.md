# Electricity Billing System

A robust, full-stack electricity billing application built with **Spring Boot** and **Supabase (PostgreSQL)**. This system streamlines customer management, meter reading, bill generation, and payment tracking with a modern, responsive user interface.

## 👤 Author
- **Name**: Sudhanwa
- **GitHub**: [sudhanwa755](https://github.com/sudhanwa755)
- **Repository**: [ElectricityBilling-Springboot-supabase](https://github.com/sudhanwa755/ElectricityBilling-Springboot-supabase)
- **Email**: [sudhanwalatur@gmail.com](mailto:sudhanwalatur@gmail.com)

---

## 🚀 Key Features

### 🎨 Modern User Experience
- **Responsive Dashboard**: Real-time statistics, charts, and quick actions.
- **Dark Mode Support**: Fully integrated dark theme for better accessibility and aesthetics.
- **Interactive UI**: Built with Thymeleaf and Bootstrap 5 for a seamless experience.

### 🛠️ Core Functionality
- **Customer Management**: 
  - CRUD operations for customer profiles.
  - **Auto-generated Meter Numbers** (e.g., `M17325...`) for reliable tracking.
- **Meter Readings**: 
  - Record current and previous readings.
  - Automatic validation and consumption calculation.
- **Billing Engine**: 
  - Automated bill generation based on tariff slabs.
  - Support for fixed charges, energy charges, and taxes.
  - **Bill Repair**: Automatic detection and repair of inconsistent bill data.
- **Reports**: 
  - Monthly revenue and usage tracking.
  - Exportable data formats.

### 🔒 Security & Architecture
- **Role-Based Access Control (RBAC)**: Secure Admin and User roles using Spring Security.
- **Supabase Integration**: Cloud-hosted PostgreSQL database for scalability.
- **Data Integrity**: Application-level validation and database constraints.

---

## 🛠️ Tech Stack

- **Backend**: Java 17, Spring Boot 3.3 (Web, Data JPA, Security, Validation)
- **Database**: PostgreSQL (via Supabase)
- **Frontend**: Thymeleaf, Bootstrap 5, Chart.js, Bootstrap Icons
- **Build Tool**: Maven

---

## ⚙️ Setup & Installation

### 1. Prerequisites
- Java Development Kit (JDK) 17 or higher
- Maven 3.8+
- A [Supabase](https://supabase.com/) account and project

### 2. Clone the Repository
```bash
git clone https://github.com/sudhanwa755/ElectricityBilling-Springboot-supabase.git
cd ElectricityBilling-Springboot-supabase
```

### 3. Configure Database
Update `src/main/resources/application.properties` with your Supabase credentials:

```properties
spring.datasource.url=jdbc:postgresql://<YOUR_SUPABASE_HOST>:5432/postgres?sslmode=require
spring.datasource.username=<YOUR_DB_USER>
spring.datasource.password=<YOUR_DB_PASSWORD>

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Run the Application
```bash
mvn clean spring-boot:run
```

The application will start at `http://localhost:8082` (or the port configured in properties).

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](https://github.com/sudhanwa755/ElectricityBilling-Springboot-supabase/issues).

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

This project is available for use and modification. Please credit the original author.
