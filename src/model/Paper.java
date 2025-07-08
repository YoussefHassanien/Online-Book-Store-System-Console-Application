package model;

public interface Paper {

    int getStock();

    boolean addStock(int stock);

    boolean reduceStock(int quantity);
}
