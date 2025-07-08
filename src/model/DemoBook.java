package model;

public class DemoBook extends Book {

    public DemoBook(String title, int publishingYear) {
        super(title, publishingYear, 0);
    }

    @Override
    public boolean isSellable() {
        return false;
    }
}
