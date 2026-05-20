# NOSM Catalog Management System

## Architecture

Hexagonal Architecture (Ports & Adapters) with Domain-Driven Design (DDD)

```
┌─────────────────────────────────────────────────┐
│            ADAPTERS (In/Out)                    │
│  REST Controllers | JOOQ Repository             │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────┴────────────────────────────┐
│        APPLICATION (Use Cases)                  │
│  CreateCatalog | UpdateCatalog | SubmitCatalog │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────┴────────────────────────────┐
│  DOMAIN (Models & Business Logic - Core)        │
│  Catalog | Product | Entity | Segment           │
│  Ports: CatalogRepository (Interface)           │
└─────────────────────────────────────────────────┘
```

## Technology Stack

- **Java**: 25 (Latest)
- **Spring Boot**: 3.5.0
- **Database**: SQL Server 2022
- **ORM**: JOOQ 3.19.10
- **Testing**: 
  - Cucumber 7.16.1 (BDD in French)
  - JUnit 5
  - Testcontainers 1.20.1
  - AssertJ
  - Mockito

## Project Structure

```
src/
├── main/
│   ├── java/com/nosm/catalog/
│   │   ├── adapter/
│   │   │   ├── in/rest/
│   │   │   │   └── CatalogController.java
│   │   │   └── out/persistence/
│   │   │       └── CatalogRepositoryImpl.java
│   │   ├── application/
│   │   │   └── usecase/
│   │   │       ├── CreateCatalogUseCase.java
│   │   │       ├── UpdateCatalogUseCase.java
│   │   │       ├── SubmitCatalogUseCase.java
│   │   │       ├── ValidateCatalogUseCase.java
│   │   │       └── CloseCatalogUseCase.java
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   ├── Catalog.java (Aggregate Root)
│   │   │   │   ├── Product.java
│   │   │   │   ├── Entity.java
│   │   │   │   ├── Segment.java
│   │   │   │   ├── CatalogStatus.java
│   │   │   │   └── CatalogType.java
│   │   │   └── port/
│   │   │       └── CatalogRepository.java
│   │   └── NosomCatalogApplication.java
│   └── resources/
│       └── schema.sql
└── test/
    ├── java/com/nosm/catalog/
    │   ├── domain/model/
    │   │   └── CatalogTest.java
    │   └── CatalogIntegrationTest.java
    └── resources/
        └── features/fr/
            ├── catalog_lifecycle.feature
            ├── catalog_scope.feature
            └── product_configuration.feature
```

## Features (BDD Scenarios)

### 1. Catalog Lifecycle (7 scenarios)
- ✅ Creation in DRAFT status
- ✅ Modification of DRAFT catalog
- ✅ Rejection of modification on non-DRAFT
- ✅ Submission to SUBMITTED status
- ✅ Validation to VALIDATED status
- ✅ Closure to CLOSED status
- ✅ Deletion of DRAFT catalog

### 2. Catalog Scope (2 scenarios)
- ✅ Addition of legal entities
- ✅ Addition of customer segments

### 3. Product Configuration (4 scenarios)
- ✅ Product addition with volume-based pricing
- ✅ Application codes configuration
- ✅ Commission type indicators
- ✅ Billing proof configuration

## REST API Endpoints

```bash
# Create catalog
POST /api/catalogs?name=Test&type=CASH&currency=EUR
Header: X-User-Id: UTXXXX

# Update catalog
PUT /api/catalogs/{catalogId}?activationDate=2026-05-19&description=Updated
Header: X-User-Id: UTXXXX

# Submit catalog
POST /api/catalogs/{catalogId}/submit

# Validate catalog
POST /api/catalogs/{catalogId}/validate

# Close catalog
POST /api/catalogs/{catalogId}/close
```

## Running Tests

```bash
# Run unit tests
mvn test

# Run integration tests
mvn verify

# Run specific BDD feature
mvn test -Dcucumber.filter.tags="@catalog_lifecycle"
```

## Database Setup

Create SQL Server 2022 database and tables:

```sql
CREATE DATABASE nosm_catalog;
GO

-- Run schema.sql to create tables
```

Or use Testcontainers for automatic database setup in tests.

## Building

```bash
mvn clean package
```

## Running Application

```bash
mvn spring-boot:run
```

Application will start on `http://localhost:8080`

## Domain Model

### Catalog (Aggregate Root)
- Manages complete lifecycle
- Enforces business rules
- Contains products, entities, and segments

### Product
- Represents financial products/services
- Contains pricing and billing information
- Application codes and commission flags

### Entity (Value Object)
- Legal entity code and name
- Used to scope catalog

### Segment (Value Object)
- Customer segment code and name
- Optional exceptional list flag

## Contributing

Follow the hexagonal architecture and DDD principles:
1. Keep domain logic in model layer
2. Use ports for external dependencies
3. Implement adapters for persistence/API
4. Write BDD tests in French

## License

NOSM Proprietary