# Digital ID System (SWE Project)

## Overview

This project is a console-based Digital ID management system built in Java.  
It simulates a simplified identity platform where users can create, manage, and verify digital identities against different organisations such as banks, employers, tax authorities, and driving licence authorities.

The system demonstrates layered backend architecture, including domain modelling, service logic, validation rules, policy-based verification, audit logging, and automated testing with CI integration.

---

## Features

- Create Digital IDs with personal details
- Update address information
- Suspend and revoke identities
- Activate suspended identities
- Organisation-based identity verification:
    - Bank
    - Employer
    - Tax Authority
    - Driving Licence Authority
    - Central Authority (full record view)
- Fraud flag detection
- Driving test and restriction rules
- Tax restriction rules
- Audit logging for key system actions
- CLI-based user interaction (no UI)
- Unit testing using JUnit 5
- Continuous Integration via GitHub Actions (Maven build + test)

---

## Architecture

The project follows a layered architecture:

- model → Core domain objects (DigitalID, Status, OperationResult)
- repository → Data persistence layer (DigitalIDRepository)
- service → Business logic (DigitalIDService, VerificationService)
- policy → Organisation-specific verification rules
- validation → Lifecycle and state validation rules
- cli → Command-line interface
- logging → Audit logging system

- This separation ensures clear responsibility boundaries, improved testability, and scalable design.
---

## Requirements

- Java 25+
- Maven 3.9+

---

## How to Run

### Run Tests

```bash
mvn clean test
Run Application (CLI)

Run the Main class located in:

src/main/java/com/digitalid/cli/Main.java

You can run it from:

IntelliJ IDEA
Or via command line after compiling
Testing

The project uses JUnit 5 for unit testing.

Tests cover:

Service layer behaviour
Policy rules
Validation rules
Domain model correctness

Run all tests with:

mvn clean test
Continuous Integration

A GitHub Actions pipeline is configured to:

Build the project
Run all unit tests
Validate compilation on every push

Key Design Concepts
Separation of concerns (model / service / policy / validation)
Domain-driven structure
Stateless service design where appropriate
Rule-based verification system
Extensible organisation policy layer
CLI-driven interaction model
Audit logging for traceability