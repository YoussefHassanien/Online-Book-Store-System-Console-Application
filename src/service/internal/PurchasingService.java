package service.internal;

import model.PaperBook;
import model.ElectronicBook;
import service.external.MailService;
import service.external.ShippingService;

public class PurchasingService {

    // Static instance variable (lazy initialization)
    private static PurchasingService instance;

    private InventoryService inventoryService;
    private MailService mailService;
    private ShippingService shippingService;

    // Private constructor to prevent instantiation
    private PurchasingService() {
        this.inventoryService = InventoryService.getInstance();
    }

    // Thread-safe getInstance method
    public static synchronized PurchasingService getInstance(InventoryService inventoryService, MailService mailService, ShippingService shippingService) {
        if (instance == null) {
            instance = new PurchasingService();
        }
        instance.inventoryService = inventoryService;
        instance.mailService = mailService;
        instance.shippingService = shippingService;
        return instance;
    }

    public double buyPaperBook(PaperBook book, int quantity, String address) {
        if (book == null) {
            throw new IllegalArgumentException("Invalid paper book");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Invalid paper book quantity");
        }

        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid customer address");
        }

        if (!book.reduceStock(quantity)) {
            throw new RuntimeException("Insufficient stock for book");
        }

        if (!shippingService.ship(book, address)) {
            book.addStock(quantity);
            throw new RuntimeException("Failed to ship book");
        }

        return book.getPrice() * quantity;
    }

    public double buyElectronicBook(ElectronicBook book, String email) {

        if (book == null) {
            throw new IllegalArgumentException("Invalid paper book");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid email for buying electronic book");
        }

        if (!mailService.send(book, email)) {
            throw new RuntimeException("Failed to send electronic book: " + book.getTitle());
        }

        return book.getPrice();
    }

    public InventoryService getInventoryService() {
        return this.inventoryService;
    }

    public MailService getMailService() {
        return this.mailService;
    }

    public ShippingService getShippingService() {
        return this.shippingService;
    }
}
