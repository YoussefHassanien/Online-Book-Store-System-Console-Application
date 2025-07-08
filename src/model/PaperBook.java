package model;

public class PaperBook extends Book implements Paper {

    private int stock;

    public PaperBook(String title, int publishingYear, double price, int stock) {
        super(title, publishingYear, price);
        if (stock <= 0) {
            throw new IllegalArgumentException("Invalid paper book stock");
        }
        this.stock = stock;
    }

    @Override
    public int getStock() {
        return this.stock;
    }

    @Override
    public boolean addStock(int quantity) {
        if (quantity > 0) {
            this.stock += quantity;
            return true;
        }
        return false;
    }

    @Override
    public boolean reduceStock(int quantity) {
        if (this.stock - quantity >= 0) {
            this.stock -= quantity;
            return true;
        }
        return false;
    }

    @Override
    public boolean isSellable() {
        return true;
    }

}
