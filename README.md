<div align="center">

# ⚡ WattWise
### The Modern Electricity Billing System

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3-green?style=for-the-badge&logo=spring-boot)
![Supabase](https://img.shields.io/badge/Supabase-PostgreSQL-3ECF8E?style=for-the-badge&logo=supabase)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-purple?style=for-the-badge&logo=bootstrap)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

<br />

**WattWise** is a robust, full-stack electricity billing solution designed for efficiency and scale. Built with **Spring Boot** and **Supabase**, it streamlines the entire billing lifecycle—from meter reading to payment tracking—wrapped in a beautiful, dark-mode ready interface.

[Report Bug](https://github.com/sudhanwa755/ElectricityBilling-Springboot-supabase/issues) · [Request Feature](https://github.com/sudhanwa755/ElectricityBilling-Springboot-supabase/issues)

</div>

---

## 🚀 Why WattWise?

WattWise isn't just a billing tool; it's a complete management suite.

| 🎨 Modern UX | 🛠️ Powerful Core | 🔒 Enterprise Ready |
|:---|:---|:---|
| **Dark Mode**: Native support for light/dark themes. | **Auto-Repair**: Self-healing data consistency logic. | **RBAC**: Secure Admin & User roles. |
| **Responsive**: Works perfectly on mobile & desktop. | **Smart Billing**: Automated tariff slab calculations. | **Audit Logs**: Track every critical change. |
| **Interactive**: Real-time charts & dashboards. | **Meter Gen**: Unique ID generation (e.g., `M17325...`). | **Cloud DB**: Scalable PostgreSQL via Supabase. |

---

## 📸 Snapshots

> *Add your screenshots here to showcase the dashboard, billing page, and dark mode!*

---

## 🛠️ Tech Stack

### Backend
* Java 17
* Spring Boot 3.3 (Web, Data JPA, Security)
* Hibernate Validator

### Frontend
* Thymeleaf Template Engine
* Bootstrap 5 & Bootstrap Icons
* Chart.js for Analytics

### Database & DevOps
* PostgreSQL (Supabase)
* Flyway Migration
* Maven Build Tool

---

## ⚡ Getting Started

Get up and running in less than 5 minutes.

### Prerequisites
*   Java JDK 17+
*   Maven 3.8+
*   Supabase Account

### Installation

1.  **Clone the repo**
    ```bash
    git clone https://github.com/sudhanwa755/ElectricityBilling-Springboot-supabase.git
    cd ElectricityBilling-Springboot-supabase
    ```

2.  **Configure Database**
    Update `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://<YOUR_HOST>:5432/postgres?sslmode=require
    spring.datasource.username=<YOUR_USER>
    spring.datasource.password=<YOUR_PASSWORD>
    ```

3.  **Ignite** 🚀
    ```bash
    mvn clean spring-boot:run
    ```
    Visit `http://localhost:8082` and log in!

---

## 👤 Author

<div align="center">

**Sudhanwa**

[![GitHub](https://img.shields.io/badge/GitHub-sudhanwa755-181717?style=flat&logo=github)](https://github.com/sudhanwa755)
[![Email](https://img.shields.io/badge/Email-sudhanwalatur@gmail.com-D14836?style=flat&logo=gmail)](mailto:sudhanwalatur@gmail.com)

</div>

---

## 🤝 Contributing

Contributions make the open-source community an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

---

<div align="center">
  <sub>Built with ❤️ by Sudhanwa</sub>
</div>
