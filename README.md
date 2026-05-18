# Digital Identity Management System

## Overview

This project is a Java-based Digital Identity Management System designed to simulate how organisations may verify and manage digital identities securely.

The system supports identity creation, verification, suspension, revocation, restriction handling, fraud detection, expiry validation, and organisation-specific verification policies.

The project was developed using object-oriented programming principles and layered architecture concepts.

---

# Features

- Create digital identities
- Auto-generated identity IDs
- Identity verification system
- Organisation-specific verification rules
- Identity suspension and revocation
- Fraud flagging
- Driving licence eligibility checks
- Tax restriction checks
- Expiry date validation
- Audit logging
- CLI-based interaction system
- Custom exception handling
- Unit testing with JUnit 5

---

# System Architecture

The application follows a layered architecture approach.

## Packages

### `model`
Contains the core domain models:
- `DigitalID`
- `OperationResult`
- `OrganisationType`
- `Status`

### `service`
Contains business service logic:
- `DigitalIDService`
- `VerificationService`

### `policy`
Contains organisation-specific verification policies:
- `OrganisationPolicyService`

### `repository`
Handles in-memory identity storage:
- `DigitalIDRepository`

### `validation`
Contains validation logic:
- `IdentityValidator`

### `logging`
Provides audit logging functionality:
- `AuditLogger`

### `cli`
Provides command-line interaction:
- `CLIHandler`

### `exception`
Contains custom exceptions:
- `InvalidIdentityException`

---

# Key Design Concepts

## Object-Oriented Programming
The system demonstrates:
- Encapsulation
- Abstraction
- Separation of concerns
- Modular design

## Validation and Policy Separation
Validation logic and organisation-specific verification rules are separated into dedicated classes to improve maintainability and readability.

## Custom Exception Handling
The system includes a custom `InvalidIdentityException` for invalid operations and invalid identity data.

---

# Verification Rules

Different organisations receive different levels of identity information.

| Organisation | Information Access |
|---|---|
| Bank | Name, DOB, status, expiry |
| Employer | Name and status |
| Tax Authority | Tax restriction information |
| Driving Licence Authority | Driving eligibility information |
| Central Authority | Full identity details |

---

# Running the Application

## Requirements

- Java 17+
- Maven
- IntelliJ IDEA (recommended)

## Run the CLI

Run the `Main` class:

```bash
mvn compile
mvn exec:java
```

---

# Running Tests

Run all tests using:

```bash
mvn test
```

The project includes unit tests for:
- Models
- Services
- Validation
- Verification policies

---

# Example Features Demonstrated

- Identity lifecycle management
- Secure verification handling
- Role-based data disclosure
- Fraud detection
- Expiry validation
- Audit logging
- Exception handling

---

# Design Decisions

Several design decisions were made to improve maintainability and scalability:

- IDs are auto-generated to prevent collisions and simplify identity creation.
- Verification rules are separated from core services using `OrganisationPolicyService`.
- Validation logic is isolated within `IdentityValidator`.
- Audit logging is separated into its own logging layer.
- Different organisations receive restricted identity data based on role.
- In-memory storage was used to keep the project lightweight and focused on core software engineering principles rather than infrastructure.

# Future Improvements

Potential future enhancements include:
- Database integration
- REST API support
- Authentication and authorisation
- Encryption for identity data
- Persistent audit storage
- Web interface

---

