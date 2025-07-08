# Online Book Store System - Console Application

A comprehensive Java-based console application for managing an online book store. This system implements object-oriented programming principles, design patterns, and provides functionality for inventory management, book purchasing, and automated testing.

## 📋 Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Design Patterns](#design-patterns)
- [Class Structure](#class-structure)
- [Usage](#usage)
- [Testing](#testing)
- [Documentation](#api-documentation)

## ✨ Features

### Core Functionality

- **Multi-type Book Management**: Support for Paper Books, Electronic Books, and Demo Books
- **Inventory Management**: Add, remove, and track book quantities
- **Purchase System**: Complete buying workflow with validation and external service integration
- **Automated ISBN Generation**: Random hexadecimal ISBN generation for each book
- **Outdated Book Management**: Identify and remove books based on publishing year
- **Type-safe Electronic Book Formats**: Support for PDF and EPUB formats only

### Advanced Features

- **Singleton Pattern Implementation**: Ensures single instance of critical services
- **Comprehensive Validation**: Input validation for all book properties and operations
- **Mock Service Integration**: Simulated mail and shipping services for testing
- **Automated Testing Suite**: Comprehensive test coverage with parameterized tests
- **Error Handling**: Robust exception handling throughout the application

## 🏗️ Architecture

The application follows a layered architecture pattern:

```
src/
├── Main.java       # Application entry point
├── model/          # Data models and entities
├── service/        # Business logic layer
│   ├── internal/   # Core business services
│   └── external/   # External service interfaces
└── test/           # Comprehensive test suite
```

## 🎯 Design Patterns

### 1. Singleton Pattern

- **InventoryService**: Ensures single inventory instance across the application
- **PurchasingService**: Centralized purchasing logic with single instance

### 2. Strategy Pattern

- **Book Types**: Different book types (Paper, Electronic, Demo) with specific behaviors
- **File Types**: Enum-based type safety for electronic book formats

### 3. Factory Pattern

- **Book Creation**: Constructors with validation and automatic property setting

## 📦 Class Structure

### Model Layer (`model/`)

#### Abstract Classes

- **`Book`**: Base class for all book types
  - Properties: `isbn`, `title`, `publishingYear`, `price`
  - Static quantity tracking
  - Automatic ISBN generation
  - Abstract method: `isSellable()`

#### Concrete Classes

- **`PaperBook`**: Physical books with stock management
- **`ElectronicBook`**: Digital books with file type validation
- **`DemoBook`**: Non-sellable sample books

#### Interfaces

- **`Paper`**: Stock management interface
- **`Electronic`**: File type management interface

#### Enums

- **`ElectronicFileType`**: PDF and EPUB format support

### Service Layer (`service/`)

#### Internal Services

- **`InventoryService`**:

  - Singleton pattern implementation
  - Book inventory management
  - Outdated book identification and removal
  - Thread-safe operations

- **`PurchasingService`**:
  - Singleton pattern implementation
  - Purchase workflow management
  - Integration with external services
  - Transaction validation and rollback

#### External Services

- **`MailService`**: Email delivery interface
- **`ShippingService`**: Physical shipping interface

### Test Layer (`test/`)

- **`QuantumBookStoreTest`**: Comprehensive automated testing
  - Parameterized test methods
  - Mock service implementations
  - End-to-end workflow testing

## 💻 Usage

### Running the Application

```java
// Main entry point
public class Main {
    public static void main(String[] args) {
        QuantumBookStoreTest test = new QuantumBookStoreTest();
        test.runAllTests();
    }
}
```

### Basic Operations

#### Adding Books to Inventory

```java
// Add Paper Book
InventoryService inventory = InventoryService.getInstance();
PaperBook paperBook = new PaperBook("Java Programming", 2023, 29.99, 50);
inventory.addPaperBook(paperBook, 10);

// Add Electronic Book
ElectronicBook eBook = new ElectronicBook("Python Guide", 2024, 19.99, ElectronicFileType.PDF);
inventory.addElectronicBook(eBook);

// Add Demo Book
DemoBook demo = new DemoBook("Free Sample", 2024);
inventory.addDemoBook(demo);
```

#### Purchasing Books

```java
PurchasingService purchasing = PurchasingService.getInstance();

// Buy Paper Book
purchasing.buyPaperBook(paperBook, 2, "123 Main St, City, Country");

// Buy Electronic Book
purchasing.buyElectronicBook(eBook, "customer@email.com");
```

#### Inventory Management

```java
// Get outdated books (older than 5 years)
List<Book> outdatedBooks = inventory.getOutdatedBooks(5);

// Remove outdated books
Map<String, Book> updatedInventory = inventory.removeOutdatedBooks(5);
```

## 🧪 Testing

### Automated Test Suite

The application includes a comprehensive test suite with the following capabilities:

#### Test Categories

1. **Inventory Management Tests**

   - Adding different book types
   - Inventory status reporting
   - Outdated book operations

2. **Purchase Workflow Tests**

   - Paper book purchasing with shipping
   - Electronic book purchasing with email delivery
   - Demo book restrictions

3. **Validation Tests**
   - Input parameter validation
   - Business rule enforcement
   - Error handling verification

#### Running Tests

```java
// Create test instance
QuantumBookStoreTest test = new QuantumBookStoreTest();

// Run all tests
test.runAllTests();

// Run individual tests
test.testAddPaperBookToInventory("Title", 2023, 29.99, 50, 10);
test.testBuyPaperBook("Title", 2023, 29.99, 50, 5, "Address");
test.testGetOutdatedBooks(3);
```

#### Sample Test Output

```
=== Starting Quantum Book Store Tests ===

1. Testing adding books to inventory:
✓ Successfully added paper book: Java Programming with quantity: 10
✓ Successfully added electronic book: Python Guide (PDF)
✓ Successfully added demo book: Free Sample Book

2. Testing getting outdated books:
✓ Found 2 outdated books (older than 2 years)
  - Old Java Book (Published: 2020)
  - Legacy Programming Guide (Published: 2019)

3. Testing buying books:
✓ Successfully bought 3 copies of: Advanced Java
  Total cost: $119.97
  Remaining stock: 17
  Shipped to: 123 Main St, City, Country
```

## 📚 Documentation

### Core Classes

#### Book (Abstract)

- `setIsbn()`: Generates random hexadecimal ISBN
- `setTitle(String)`: Sets and validates book title
- `setPublishingYear(int)`: Validates year is not in future
- `setPrice(double)`: Sets book price with validation
- `isSellable()`: Abstract method for sellability check

#### InventoryService (Singleton)

- `getInstance(Map<String, Book>)`: Gets singleton instance
- `addPaperBook(PaperBook, int)`: Adds paper book with quantity
- `addElectronicBook(ElectronicBook)`: Adds electronic book
- `addDemoBook(DemoBook)`: Adds demo book
- `getOutdatedBooks(int)`: Returns books older than specified years
- `removeOutdatedBooks(int)`: Removes and returns updated inventory

#### PurchasingService (Singleton)

- `buyPaperBook(PaperBook, int, String)`: Purchase with shipping
- `buyElectronicBook(ElectronicBook, String)`: Purchase with email delivery
- `buyDemoBook(DemoBook)`: Throws error (demo books not sellable)

### Validation Rules

- **Title**: Non-null, non-empty string
- **Publishing Year**: Positive integer, not in future
- **Price**: Positive double value
- **Stock**: Non-negative integer for paper books
- **Email**: Must contain '@' symbol
- **Address**: Non-null, non-empty string
- **Electronic File Type**: Must be PDF or EPUB only

## 📄 License

This project is created for educational purposes as part of the Fawry Internship Assessment.
