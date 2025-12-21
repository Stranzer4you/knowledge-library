# Knowledge Library – Spring Boot Application

## 1.Overview

        This is a Spring Boot–based REST API for managing different types of knowledge entries (Text, Link, Quote, Composite).
        The application uses an embedded H2 database, Spring Data JPA, validation, global exception handling, and basic authentication.

  Authentication & Authorization Overview
  
    The application uses role-based authorization implemented with HTTP Basic Authentication.

     Public APIs (No Authentication Required)

        GET /knowledge-library/api/v1/knowledge
        GET /knowledge-library/api/v1/knowledge/{id}

     Secured APIs (Authentication Required)

        Accessible by both ADMIN and USER

        Create Text Knowledge
        Create Link Knowledge
        Create Quote Knowledge

        Accessible by ADMIN only

        Create Composite Knowledge
        Delete Knowledge

        Summary Table 

        | Endpoint Type              | Authentication | Roles        |
        | -------------------------- | -------------- | -----------  |
        | Get All / Get By ID        | ❌ Not Required | Public      |
        | Create Text / Link / Quote | ✅ Required     | ADMIN, USER |
        | Create Composite           | ✅ Required     | ADMIN       |
        | Delete Knowledge           | ✅ Required     | ADMIN       |



## 2.Tech Stack

    Java, Spring Boot, Spring Data JPA, H2 (Embedded Database), Maven, Spring Security (Basic Auth)

## 3.JDK & Build Tool Requirements

     Java Development Kit (JDK):
         JDK 17.0.10 (used for development & testing)
     Build Tool:
         Apache Maven 3.9.9


## 4.How to Run the Application

   You can run the application using IntelliJ IDEA or directly from the terminal.

   Option 1: Run Using IntelliJ IDEA

        Import the project as a Maven project.
        Ensure JDK 17.0.10 is configured for the project.
        Locate the main class annotated with @SpringBootApplication.
        Right-click and select Run.

   Option 2: Run Using Terminal (Maven)
   
        mvn clean install
        mvn spring-boot:run
 
   The application will start at:
     
        http://localhost:8080


## 5.How to run tests

   Tests can be executed using IntelliJ IDEA or directly from the terminal.

   Option 1: Run Tests Using IntelliJ IDEA
        
        Open the project in IntelliJ IDEA.
        Ensure the project is using JDK 17.0.10.
        Navigate to the src/test/java directory.
        Right-click on:
        a specific test class → Run
        or the test package → Run all tests

   Option 2: Run Tests Using Terminal (Maven)

        From the project root directory, run:
        mvn test

   To clean and run tests:

        mvn clean test

   Notes
        Tests use an in-memory H2 database.
        No external dependencies are required to run tests.

## 6.Initial Data Loading

   The application does not preload any sample data by default.

        Database tables are automatically created on application startup using JPA/Hibernate.
        The embedded H2 database starts empty.
        Data can be inserted using the exposed REST APIs (for example, via curl or Postman).


## 7.In-Memory Users and Passwords
        
        | Role  | Username | Password |
        | ----- | -------- | -------- |
        | ADMIN | admin    | admin123 |
        | USER  | user     | user123  |



## 8.Sample Curls for testing

--USER (Secured) – Create Text Knowledge

curl -X POST http://localhost:8080/knowledge-library/api/v1/knowledge/text \
-u user:user123 \
-H "Content-Type: application/json" \
-d '{
"title": "Constructor Injection",
"description": "Why constructor injection is preferred",
"content": "Constructor injection makes dependencies immutable and improves testability."
}'


--ADMIN (Secured) – Create Link Knowledge

curl -X POST http://localhost:8080/knowledge-library/api/v1/knowledge/link \
-u admin:admin123 \
-H "Content-Type: application/json" \
-d '{
"title": "Spring Framework",
"description": "Official Spring Framework documentation",
"url": "https://spring.io/projects/spring-framework"
}'



--USER (Secured) – Create Quote Knowledge

curl -X POST http://localhost:8080/knowledge-library/api/v1/knowledge/quote \
-u user:user123 \
-H "Content-Type: application/json" \
-d '{
"title": "Clean Code Quote",
"description": "A famous quote about clean code",
"quoteText": "Clean code is simple and direct.",
"author": "Robert C. Martin"
}'


ADMIN ONLY (Secured) – Create Composite Knowledge

curl -X POST http://localhost:8080/knowledge-library/api/v1/knowledge/composite \
-u admin:admin123 \
-H "Content-Type: application/json" \
-d '{
"title": "Spring Boot Basics Collection",
"description": "A curated collection of Spring Boot learning materials",
"knowledgeIds": [1, 2]
}'


 PUBLIC – Get All Knowledge (No Authentication)

 curl -X GET "http://localhost:8080/knowledge-library/api/v1/knowledge?type=TextKnowledge&sortBy=title&sortOrder=asc"



--PUBLIC – Get Knowledge By ID (No Authentication)

curl -X GET http://localhost:8080/knowledge-library/api/v1/knowledge/1


--ADMIN ONLY (Secured) – Delete Knowledge

curl -X DELETE http://localhost:8080/knowledge-library/api/v1/knowledge/1 \
-u admin:admin123


## 9.Engineering Notes

    Key Design Choices

        Adopted a layered architecture (Controller → Service → Repository) to enforce separation of concerns and improve maintainability.
        Modeled different knowledge types using inheritance with a common base entity, enabling polymorphic behavior while keeping the API consistent.
        Implemented role-based access control using Spring Security with HTTP Basic authentication to clearly demonstrate authorization rules.
        Centralized exception handling using @RestControllerAdvice to ensure consistent, well-structured JSON error responses.
        Used DTOs and mappers to decouple persistence models from API contracts.
        Leveraged Spring Data JPA custom queries (@Query) to demonstrate non-trivial data access logic.
        Enabled embedded H2 (file-based) storage for easy local setup with persistent data across restarts.

    Improvements With Additional Time

        Strengthen domain-level validations across all knowledge types, with additional checks for composite knowledge creation and data consistency.
        Strengthen business rules to enforce better data quality, consistency, and domain correctness across knowledge types.
        Improve authorization edge-case handling (e.g., USER attempting ADMIN-only operations) with additional integration tests.
        Extend composite knowledge validation to prevent duplicate knowledge IDs and enforce stricter composition rules.
        Introduce database-level constraints (e.g., unique and foreign key constraints) to reinforce data integrity alongside application-level validations.
