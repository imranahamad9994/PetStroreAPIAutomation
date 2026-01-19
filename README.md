# Swagger Petstore API Automation

This project demonstrates end-to-end API automation for the Swagger Petstore application using Rest Assured and TestNG.  
It covers functional, negative, and contract-level validations following real-world QA best practices.

---

## 🛠 Tech Stack
- Java
- Rest Assured
- TestNG
- Maven

---

## 📌 APIs Covered

### User API
- Create, Read, Update, Delete user
- Negative scenarios (deleted user, invalid operations)

### Store API
- Order lifecycle (Create → Get → Delete)
- Inventory validation

### Pet API
- CRUD operations
- Array handling (`photoUrls`)
- Array of objects (`tags`)
- Filter pets by status (`findByStatus`)
- Multipart image upload
- JSON Schema validation (contract testing)

---

## ⭐ Key Highlights
- Dependency-based test execution using TestNG
- Request & response validation using Hamcrest matchers
- Path and query parameter handling
- JSON Schema validation for contract testing
- Multipart file upload automation
- Handles real Swagger Petstore API inconsistencies gracefully

---

## ▶️ How to Run
```bash
mvn test
