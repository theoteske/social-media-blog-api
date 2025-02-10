# Social Media Blog API

A backend for a hypothetical social media app akin to Twitter.

## Overview

A RESTful backend API built with Spring Boot for a social media platform that handles user authentication and message management. This project demonstrates RESTful design principles and robust error handling.

## Key Features

- Secure user registration and authentication
- CRUD operations for messages
- User-specific message retrieval
- Proper error handling and status codes
- Data validation and sanitization
- Database relationship management

## Tech Stack

- Spring Boot 2.5.5
  - Spring Boot Web (REST APIs)
  - Spring Boot Data JPA (Data persistence)
  - Spring Boot Test (Testing)
- H2 Database 2.1.214
- Java 11
- Maven
- SQL

## Getting Started

### Prerequisites

- Java 11 JDK
- Maven 3.6+

### Running the Application

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The server will start on `localhost:8080`.

### Development Tools

Recommended VS Code extensions:
- Extension Pack for Java
- Spring Boot Extension Pack

## API Endpoints

### Authentication

#### Register New Account
```http
POST /register
Content-Type: application/json

Request Body:
{
    "username": "john_doe",
    "password": "password123"
}

Responses:
200 OK: Returns created account with ID
409 Conflict: Username already exists
400 Bad Request: Invalid username or password
```

#### Login
```http
POST /login
Content-Type: application/json

Request Body:
{
    "username": "john_doe",
    "password": "password123"
}

Responses:
200 OK: Returns account details
401 Unauthorized: Invalid credentials
```

### Messages

#### Create Message
```http
POST /messages
Content-Type: application/json

Request Body:
{
    "messageText": "Hello, World!",
    "postedBy": 1
}

Responses:
200 OK: Returns created message with ID
400 Bad Request: Invalid message text or user ID
```

#### Get All Messages
```http
GET /messages

Response:
200 OK: Returns array of all messages
```

#### Get Message by ID
```http
GET /messages/{messageId}

Response:
200 OK: Returns message or empty if not found
```

#### Delete Message
```http
DELETE /messages/{messageId}

Responses:
200 OK: Returns 1 if deleted, empty if not found
```

#### Update Message
```http
PATCH /messages/{messageId}
Content-Type: application/json

Request Body:
{
    "messageText": "Updated message text"
}

Responses:
200 OK: Returns 1 if updated
400 Bad Request: Invalid message text or not found
```

#### Get User's Messages
```http
GET /accounts/{accountId}/messages

Response:
200 OK: Returns array of user's messages
```

## Database Configuration

This project uses an H2 in-memory database for development and testing purposes. Key configuration:
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

In a production environment, one would typically:
- Use a persistent database (like PostgreSQL or MySQL)
- Store sensitive credentials in environment variables
- Disable the H2 console
- Configure appropriate security settings

## Database Schema

```sql
CREATE TABLE Account (
    accountId integer primary key auto_increment,
    username varchar(255) not null unique,
    password varchar(255)
);

CREATE TABLE Message (
    messageId integer primary key auto_increment,
    postedBy integer,
    messageText varchar(255),
    timePostedEpoch long,
    foreign key (postedBy) references Account(accountId)
);
```

## Testing

The project includes comprehensive test coverage for:
- Unit tests for service layer logic
- Integration tests for REST endpoints
- Repository layer tests for data persistence

Run tests with:
```bash
mvn test
```
