# Pokemon-API
A RESTful API built with Spring Boot that allows users to register, log in, and manage Pokémon along with reviews. The project includes role-based access control using Spring Security, entity relationships like Pokémon and Review, and secure authentication via JWT.


#  Pokémon Backend API
**Pokémon API** is a RESTful backend application built with **Spring Boot**. It provides functionality for user registration and login, managing Pokémon entities, and adding reviews to them. The application uses **JWT authentication** and **Spring Security** for access control.

## Project Description
This project simulates a basic system for managing Pokémon characters where users can:

- Register and log in to their accounts
- Create, update, and delete Pokémon
- Add reviews to Pokémon
- Operate within a role-based authorization system (e.g., USER / ADMIN)

## Entity Relationships
- **User ⬌ Role**  
  `Many-to-Many`: A user can have multiple roles and each role can belong to multiple users.
- **Pokemon ⬌ Review**  
  `One-to-Many`: A single Pokémon can have multiple reviews.

## Authentication & Authorization
- APIs are secured using Spring Security and JWT.
- Upon successful login, the user receives a **JWT Token** that must be included in the headers of protected requests:
```http
Authorization: Bearer <JWT_TOKEN>
