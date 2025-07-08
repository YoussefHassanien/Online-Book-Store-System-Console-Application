package model;

public class ElectronicBook extends Book implements Electronic {

    ElectronicFileType type;

    public ElectronicBook(String title, int publishingYear, double price, ElectronicFileType type) {
        super(title, publishingYear, price);
        if (type != ElectronicFileType.EPUB && type != ElectronicFileType.PDF) {
            throw new IllegalArgumentException("Invalid electronic book file type");
        }
        this.type = type;
    }

    @Override
    public ElectronicFileType getFileType() {
        return this.type;
    }

    @Override
    public boolean isSellable() {
        return true;
    }
}
