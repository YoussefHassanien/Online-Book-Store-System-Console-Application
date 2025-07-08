package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Book;
import model.DemoBook;
import model.ElectronicBook;
import model.ElectronicFileType;
import model.PaperBook;
import service.external.MailService;
import service.external.ShippingService;
import service.internal.InventoryService;
import service.internal.PurchasingService;

public class QuantumBookStoreTest {

    private final InventoryService inventoryService;
    private final PurchasingService purchasingService;
    private final TestMailService testMailService;
    private final TestShippingService testShippingService;

    public QuantumBookStoreTest() {
        this.testMailService = new TestMailService();
        this.testShippingService = new TestShippingService();
        this.inventoryService = InventoryService.getInstance(new HashMap<>());
        this.purchasingService = PurchasingService.getInstance(inventoryService, testMailService, testShippingService);
    }

    public boolean testAddPaperBookToInventory(String title, int publishingYear, double price, int stock, int quantity) {
        try {
            PaperBook book = new PaperBook(title, publishingYear, price, stock);
            boolean result = inventoryService.addPaperBook(book, quantity);

            if (result) {
                System.out.println("Successfully added paper book: " + title + " with quantity: " + quantity);
                return true;
            } else {
                System.out.println("Failed to add paper book: " + title);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error adding paper book: " + e.getMessage());
            return false;
        }
    }

    public boolean testAddElectronicBookToInventory(String title, int publishingYear, double price, ElectronicFileType type) {
        try {
            ElectronicBook book = new ElectronicBook(title, publishingYear, price, type);
            boolean result = inventoryService.addElectronicBook(book);

            if (result) {
                System.out.println("Successfully added electronic book: " + title + " (" + type + ")");
                return true;
            } else {
                System.out.println("Failed to add electronic book: " + title);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error adding electronic book: " + e.getMessage());
            return false;
        }
    }

    public boolean testAddDemoBookToInventory(String title, int publishingYear) {
        try {
            DemoBook book = new DemoBook(title, publishingYear);
            boolean result = inventoryService.addDemoBook(book);

            if (result) {
                System.out.println("Successfully added demo book: " + title);
                return true;
            } else {
                System.out.println("Failed to add demo book: " + title);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error adding demo book: " + e.getMessage());
            return false;
        }
    }

    public List<Book> testGetOutdatedBooks(int pastYears) {
        try {
            List<Book> outdatedBooks = inventoryService.getOutdatedBooks(pastYears);
            System.out.println("Found " + outdatedBooks.size() + " outdated books (older than " + pastYears + " years)");

            for (Book book : outdatedBooks) {
                System.out.println("  - " + book.getTitle() + " (Published: " + book.getPublishingYear() + ")");
            }

            return outdatedBooks;
        } catch (Exception e) {
            System.out.println("Error getting outdated books: " + e.getMessage());
            return null;
        }
    }

    public Map<String, Book> testRemoveOutdatedBooks(int pastYears) {
        try {
            int initialCount = inventoryService.getInventory().size();
            Map<String, Book> remainingInventory = inventoryService.removeOutdatedBooks(pastYears);
            int finalCount = remainingInventory.size();
            int removedCount = initialCount - finalCount;

            System.out.println("Removed " + removedCount + " outdated books (older than " + pastYears + " years)");
            System.out.println("  Remaining inventory: " + finalCount + " books");

            return remainingInventory;
        } catch (Exception e) {
            System.out.println("Error removing outdated books: " + e.getMessage());
            return null;
        }
    }

    public boolean testBuyPaperBook(String title, int publishingYear, double price, int initialStock, int quantity, String address) {
        try {
            PaperBook book = new PaperBook(title, publishingYear, price, initialStock);
            inventoryService.addPaperBook(book, 0);

            double totalCost = purchasingService.buyPaperBook(book, quantity, address);

            System.out.println("Successfully bought " + quantity + " copies of: " + title);
            System.out.println("  Total cost: $" + totalCost);
            System.out.println("  Remaining stock: " + book.getStock());
            System.out.println("  Shipped to: " + address);
            return true;
        } catch (Exception e) {
            System.out.println("Error buying paper book: " + e.getMessage());
            return false;
        }
    }

    public boolean testBuyElectronicBook(String title, int publishingYear, double price, ElectronicFileType type, String email) {
        try {
            ElectronicBook book = new ElectronicBook(title, publishingYear, price, type);
            inventoryService.addElectronicBook(book);

            double totalCost = purchasingService.buyElectronicBook(book, email);

            System.out.println("Successfully bought electronic book: " + title);
            System.out.println("  Format: " + type);
            System.out.println("  Total cost: $" + totalCost);
            System.out.println("  Sent to: " + email);
            return true;
        } catch (Exception e) {
            System.out.println("Error buying electronic book: " + e.getMessage());
            return false;
        }
    }

    // Should fail
    public boolean testBuyDemoBook(String title, int publishingYear) {
        try {
            DemoBook book = new DemoBook(title, publishingYear);
            inventoryService.addDemoBook(book);

            purchasingService.buyDemoBook(book);

            System.out.println("Demo book purchase should have failed but didn't");
            return false;
        } catch (Error e) {
            System.out.println("Correctly prevented demo book purchase: " + e.getMessage());
            return true;
        } catch (Exception e) {
            System.out.println("Unexpected error buying demo book: " + e.getMessage());
            return false;
        }
    }

    public void testInventoryStatus() {
        Map<String, Book> inventory = inventoryService.getInventory();
        System.out.println("\n=== Inventory Status ===");
        System.out.println("Total books in inventory: " + inventory.size());

        int paperBooks = 0, electronicBooks = 0, demoBooks = 0;

        for (Book book : inventory.values()) {
            if (book instanceof PaperBook paperBook) {
                paperBooks++;
                System.out.println("Paper: " + book.getTitle() + " (Stock: " + paperBook.getStock() + ")");
            } else if (book instanceof ElectronicBook electronicBook) {
                electronicBooks++;
                System.out.println("Electronic: " + book.getTitle() + " (" + electronicBook.getFileType() + ")");
            } else if (book instanceof DemoBook) {
                demoBooks++;
                System.out.println("Demo: " + book.getTitle());
            }
        }

        System.out.println("Summary: " + paperBooks + " paper, " + electronicBooks + " electronic, " + demoBooks + " demo books");
    }

    // Mock implementations for testing
    private class TestMailService implements MailService {

        @Override
        public boolean send(ElectronicBook book, String email) {
            if (email != null && !email.trim().isEmpty() && email.contains("@")) {
                System.out.println("Sent " + book.getTitle() + " successfully to " + email);
                return true;
            }
            return false;
        }
    }

    private class TestShippingService implements ShippingService {

        @Override
        public boolean ship(PaperBook book, String address) {

            if (address != null && !address.trim().isEmpty()) {

                System.out.println("Shipped " + book.getTitle() + " successfully to " + address);
                return true;
            }
            return false;
        }
    }

    public void runAllTests() {
        System.out.println("=== Starting Quantum Book Store Tests ===\n");

        // Test 1: Adding books to inventory
        System.out.println("1. Testing adding books to inventory:");
        testAddPaperBookToInventory("Java Programming", 2023, 29.99, 50, 10);
        testAddElectronicBookToInventory("Python Guide", 2022, 19.99, ElectronicFileType.PDF);
        testAddDemoBookToInventory("Free Sample Book", 2024);

        // Test 2: Getting outdated books
        System.out.println("\n2. Testing getting outdated books:");
        testGetOutdatedBooks(2);

        // Test 3: Buying books
        System.out.println("\n3. Testing buying books:");
        testBuyPaperBook("Advanced Java", 2023, 39.99, 20, 3, "123 Main St, City, Country");
        testBuyElectronicBook("Digital Marketing", 2024, 24.99, ElectronicFileType.EPUB, "customer@email.com");
        testBuyDemoBook("Sample Book", 2024);

        // Test 4: Inventory status
        testInventoryStatus();

        // Test 5: Removing outdated books
        System.out.println("\n4. Testing removing outdated books:");
        testRemoveOutdatedBooks(3);

        System.out.println("\n=== All Tests Completed ===");
    }
}
