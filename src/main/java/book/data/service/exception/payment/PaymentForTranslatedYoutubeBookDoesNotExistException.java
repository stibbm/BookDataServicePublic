package book.data.service.exception.payment;

public class PaymentForTranslatedYoutubeBookDoesNotExistException extends RuntimeException {
  public PaymentForTranslatedYoutubeBookDoesNotExistException(String message) {
    super(message);
  }

  public PaymentForTranslatedYoutubeBookDoesNotExistException() {}
}
