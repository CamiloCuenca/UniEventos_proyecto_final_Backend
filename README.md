![logo-Claro.svg](https://firebasestorage.googleapis.com/v0/b/unieventos-1c779.appspot.com/o/logoNuevo.svg?alt=media&token=87d39113-8201-46e0-8005-36be321f1e13)
# UniEventos - Ticket Selling Platform

## Table of Contents
- [Project Overview](#project-overview)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Setup](#setup)
- [Contributors](#contributors)

## Project Overview

UniEventos is a ticket-selling platform designed to help users register, manage, and buy tickets for events. It features user authentication, role management, and secure payments. This platform is developed using modern technologies to ensure scalability, security, and ease of use.

---

## Technologies Used

- **Java**  
  ![Java Logo](https://upload.wikimedia.org/wikipedia/en/3/30/Java_programming_language_logo.svg)  
  The core programming language for the backend development.

- **Spring Boot**  
  ![Spring Boot Logo](https://www.fontana.com.ar/wp-content/uploads/2018/10/spring-boot-logo.png)  
  Framework for building production-ready Java applications with ease.

- **Lombok**  
  ![Lombok Logo](https://avatars.githubusercontent.com/u/45949248?s=280&v=4)  
  Reduces boilerplate code like getters, setters, and constructors.

- **Spring Security**  
  ![Spring Security Logo](https://miro.medium.com/v2/resize:fit:1260/1*vQ5I4c8inMOoUfGB7BunCQ.png)  
  Ensures the application is secure with features like role-based access control (RBAC) and password encryption.

- **JWT (JSON Web Tokens)**  
  ![JWT Logo](https://jwt.io/img/logo-asset.svg)  
  Used for secure user authentication.

- **Java Simple Email**  
  
  For sending transactional emails, such as activation codes and password recovery.

- **Firebase**  
  ![Firebase Logo](https://www.gstatic.com/mobilesdk/160503_mobilesdk/logo/2x/firebase_96dp.png)  
    integration with firebase to save images.

---

## Features

- User registration, login, and password recovery
- Event creation and management
- Role-based access control (Admin, User)
- Secure JWT authentication
- integration with firebase to save images
- Sending account activation codes and password recovery links via email

---

## Setup

### Prerequisites

- Java 17 
- Gradle 
- MongoDB
- Firebase

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/CamiloCuenca/UniEventos_proyecto_final.git
2. Navigate to the project directory:
    ```bash
    cd uni-eventos
3. Run the project using Gradle:
   ```bash
    ./gradlew bootRun

### contributors
-  Juan Camilo Cuenca Sepulveda - https://github.com/CamiloCuenca
-  Brandon Montealegre - https://github.com/Twolifelaw

