package model;

import java.security.SecureRandom;

public abstract class Book {

    private String isbn;
    private String title;
    private int publishingYear;
    private double price;

    protected Book(String title, int publishingYear, double price) {
        setIsbn();
        if (!setTitle(title)) {
            throw new IllegalArgumentException("Invalid book title");
        }
        if (!setPublishingYear(publishingYear)) {
            throw new IllegalArgumentException("Invalid book publishing year");
        }
        if (!setPrice(price)) {
            throw new IllegalArgumentException("Invalid price");
        }

    }

    private void setIsbn() {
        // Generate random hexadecimal string
        SecureRandom random = new SecureRandom();
        StringBuilder hexString = new StringBuilder();

        // Generate 13 characters for ISBN-like format
        for (int i = 0; i < 13; i++) {
            int randomHex = random.nextInt(16);
            hexString.append(Integer.toHexString(randomHex));
        }

        this.isbn = hexString.toString().toUpperCase();
    }

    private boolean setTitle(String title) {
        if (title != null && !title.trim().isEmpty()) {
            this.title = title;
            return true;
        }
        return false;
    }

    private boolean setPublishingYear(int publishingYear) {
        int currentYear = java.time.Year.now().getValue();
        if (publishingYear > 0 && publishingYear <= currentYear) {
            this.publishingYear = publishingYear;
            return true;
        }
        return false;
    }

    private boolean setPrice(double price) {
        if (price >= 0) {
            this.price = price;
            return true;
        }
        return false;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public int getPublishingYear() {
        return this.publishingYear;
    }

    public double getPrice() {
        return this.price;
    }

    public abstract boolean isSellable();
}
