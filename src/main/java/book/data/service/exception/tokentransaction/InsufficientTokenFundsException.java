package book.data.service.exception.tokentransaction;

public class InsufficientTokenFundsException extends RuntimeException {
  public InsufficientTokenFundsException(String message) {
    super(message);
  }
}
