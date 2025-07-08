package service.external;

import model.PaperBook;

public interface ShippingService {

    boolean ship(PaperBook book, String address);
}
