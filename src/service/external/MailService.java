package service.external;

import model.ElectronicBook;

public interface MailService {

    boolean send(ElectronicBook book, String email);
}
