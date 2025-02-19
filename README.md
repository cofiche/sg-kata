# Banking System Architecture Documentation
Overview
This banking system is implemented using hexagonal architecture (ports and adapters pattern) in Spring framework. The system provides two main operations:

Deposit funds
Withdraw funds

# Architecture Components
Domain Layer (Core)
The innermost layer containing the business logic and domain models.
Domain Models

# Account

Properties: accountId, balance
Business rules for maintaining account balance
Validation logic for transactions



# Core Business Rules

Withdrawal amount cannot exceed current balance
Transaction amounts must be positive

# Application Layer (Ports)
Defines interfaces for interacting with the domain.
Input Ports (Primary/Driving Ports)

deposit(accountId, amount)
withdraw(accountId, amount)
Handles business operations and orchestrates domain objects



# Output Ports (Secondary/Driven Ports)

AccountRepository interface

findById(accountId)
save(account)
Defines how the domain interacts with data storage



# Infrastructure Layer (Adapters)
Primary Adapters (Driving Adapters)

REST Controller (AccountController)
CopyPOST /api/accounts/deposit
POST /api/accounts/withdraw

Handles HTTP requests
Input validation
Request/Response DTOs
Error handling



Secondary Adapters (Driven Adapters)

Database Adapter

Implements AccountRepository
Handles persistence operations
Database entity mappings


Transaction Logger

Logs all financial transactions
Audit trail implementation



# Data Flow
Deposit Flow

HTTP POST request received by AccountController
Controller validates request and converts to service command
AccountService implementation processes the deposit
Domain logic validates the transaction
Repository saves the updated account state
Response returned to client

Withdraw Flow

HTTP POST request received by AccountController
Controller validates request and converts to service command
AccountService checks balance sufficiency
Domain logic validates the transaction
Repository saves the updated account state
Response returned to client

# Error Handling

BalanceException
AccountNotFoundException
Technical Implementation Details

# Dependencies

Spring Boot
Spring Web
JUnit/Mockito for testing

Testing Strategy

Unit Tests

Domain logic
Service layer
Individual adapters
