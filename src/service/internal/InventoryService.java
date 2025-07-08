package service.internal;

import model.Book;
import model.PaperBook;
import model.ElectronicBook;
import model.DemoBook;
import java.util.HashMap;
import java.util.List;
import java.time.Year;
import java.util.ArrayList;
import java.util.Map;

public class InventoryService {

    // Static instance variable (lazy initialization)
    private static InventoryService instance;

    private Map<String, Book> inventory = new HashMap<>();

    // Private constructor to prevent instantiation
    private InventoryService() {
        this.inventory = new HashMap<>();
    }

    public static synchronized InventoryService getInstance(Map<String, Book> inventory) {
        if (instance == null) {
            instance = new InventoryService();
        }
        instance.inventory = new HashMap<>(inventory);
        return instance;
    }

    public boolean addPaperBook(PaperBook book, int quantity) {
        if (book != null && quantity > 0) {
            inventory.put(book.getIsbn(), book);
            book.addStock(quantity);
            return true;
        }
        return false;
    }

    public boolean addElectronicBook(ElectronicBook book) {
        if (book != null) {
            inventory.put(book.getIsbn(), book);
            return true;
        }
        return false;
    }

    public boolean addDemoBook(DemoBook book) {
        if (book != null) {
            inventory.put(book.getIsbn(), book);
            return true;
        }
        return false;
    }

    public List<Book> getOutdatedBooks(int pastYears) {
        List<Book> outdatedBooks = new ArrayList<>();
        int currentYear = Year.now().getValue();
        int cutoffYear = currentYear - pastYears;

        for (Book book : inventory.values()) {
            if (book.getPublishingYear() < cutoffYear) {
                outdatedBooks.add(book);
            }
        }

        return outdatedBooks;
    }

    public Map<String, Book> removeOutdatedBooks(int pastYears) {
        List<String> outdatedBooksIsbns = new ArrayList<>();
        int currentYear = Year.now().getValue();
        int cutoffYear = currentYear - pastYears;

        for (Book book : inventory.values()) {
            if (book.getPublishingYear() < cutoffYear) {
                outdatedBooksIsbns.add(book.getIsbn());
            }
        }

        for (String isbn : outdatedBooksIsbns) {
            inventory.remove(isbn);
        }

        return inventory;
    }

    public Map<String, Book> getInventory() {
        return this.inventory;
    }

}
