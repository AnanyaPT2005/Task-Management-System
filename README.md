# 🚀 Task Manager REST API

A secure and scalable Task Management system built using **Spring Boot**, **MySQL**, and **JWT Authentication**, with a simple frontend built using **Vanilla JavaScript (HTML, CSS, JS)**.

This project demonstrates secure backend design, role-based access control, RESTful API development, and scalable architecture principles.

---

# 📌 Project Overview

This application provides:

- User registration and login
- JWT-based stateless authentication
- Role-based access control (USER / ADMIN)
- Task CRUD operations
- Centralized error handling
- Input validation
- API versioning
- Clean layered architecture

---

# 🛠 Tech Stack

## Backend
- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA (Hibernate)
- MySQL
- JWT (io.jsonwebtoken)
- Lombok
- Maven

## Frontend
- HTML
- CSS
- Vanilla JavaScript (Fetch API)

---

# 👥 Role-Based Authorization

Two roles are supported:

## USER
- Create tasks
- View their own tasks
- Delete tasks

## ADMIN
- Create tasks
- View tasks of all users
- See task owner names
- Delete tasks

Role is stored inside the JWT token and applied dynamically using Spring Security.

---

# 📦 Task Management (CRUD)

| Method | Endpoint | Description |
|--------|----------|------------|
| POST | `/api/v1/auth/register` | Register new user |
| POST | `/api/v1/auth/login` | Login and receive JWT |
| POST | `/api/v1/tasks` | Create task |
| GET | `/api/v1/tasks` | Get tasks (Admin sees all, User sees own) |
| DELETE | `/api/v1/tasks/{id}` | Delete task |

---

# 🧱 Project Structure
# 🚀 Task Manager REST API

A secure and scalable Task Management system built using **Spring Boot**, **MySQL**, and **JWT Authentication**, with a simple frontend built using **Vanilla JavaScript (HTML, CSS, JS)**.

This project demonstrates secure backend design, role-based access control, RESTful API development, and scalable architecture principles.

---

# 🔐 Authentication & Security

- Passwords hashed using BCrypt
- Login returns JWT token
- JWT contains role information
- Custom JWT filter validates token
- Stateless authentication (no sessions)
- Role-based access enforced in service layer
- Centralized exception handling using `@ControllerAdvice`
- Input validation using `@NotBlank`, `@Email`, `@Size`
- CORS configuration enabled for frontend integration

---

# 🧱 Project Structure
controller/
service/
repository/
model/
security/
exception/
config/


# 🗄 Database Schema

## User Table
- id
- name
- email (unique)
- password (hashed)
- role (USER / ADMIN)

## Task Table
- id
- title
- description
- user_id (foreign key)

Relationship:
One User → Many Tasks

---

# 🌐 Frontend Features

- Register as USER or ADMIN
- Login and store JWT in localStorage
- Dashboard displays logged-in role
- Create tasks
- View tasks
- Admin can see owner names
- Delete tasks
- Logout functionality

All protected API calls include:
- Authorization: Bearer <token>


---

# 📘 API Documentation

Postman collection included: taskmanager-postman-collection.json


To use:
1. Import collection into Postman
2. Register a user
3. Login to obtain JWT
4. Use token for protected endpoints

---

# ⚙️ Setup Instructions

## 1️⃣ Create Database

CREATE DATABASE taskdb;

## 2️⃣Update application.properties:
spring.datasource.username=your_username
spring.datasource.password=your_password

## 3️⃣Run: mvn spring-boot:run
Run Backend runs at http://localhost:8080

## 4️⃣frontend/login.html(open in browser or live server in vscode)

# 📈 Scalability Considerations

This system is designed with scalability in mind:

## 1️⃣ Stateless Authentication
- JWT eliminates server-side session storage.
- Enables horizontal scaling behind load balancers.
- No dependency on in-memory session state.

## 2️⃣ Layered Architecture
- Clear separation between `controller`, `service`, and `repository`.
- Improves maintainability and modularity.
- New modules (e.g., Projects, Comments) can be added without restructuring existing code.

## 3️⃣ API Versioning
- All endpoints follow `/api/v1/`.
- Allows safe backward-compatible upgrades (e.g., `/api/v2/` in future).
- Prevents breaking changes for existing clients.

## 4️⃣ Database Scalability
- MySQL can be migrated to managed cloud services (e.g., AWS RDS, Azure SQL).
- Read replicas can be added to improve read performance.
- Database indexing can optimize large datasets.

## 5️⃣ Future Enhancements
- Redis caching for frequently accessed tasks.
- Docker containerization for consistent deployments.
- CI/CD pipeline integration for automated builds and deployments.
- Cloud deployment (AWS / Azure / GCP) for production scaling.

---

## Output Screenshots
Login Page
![alt text](<login.png>)
---

Register Page
![alt text](<register.png>)
---

Dashboard Page
![alt text](<dashboard.png>)
