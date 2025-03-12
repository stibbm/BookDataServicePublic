package book.data.service.exception.checkoutsession;

public class CheckoutSessionDoesNotExistException extends RuntimeException {
    public CheckoutSessionDoesNotExistException(String message) {
        super(message);
    }

    public CheckoutSessionDoesNotExistException(){}
}
