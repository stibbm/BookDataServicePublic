package book.data.service.exception.tokentransaction;

public class TokentTransactionDoesNotExistException extends RuntimeException {
    public TokentTransactionDoesNotExistException(String message) {
        super(message);
    }

    public TokentTransactionDoesNotExistException() {}
}
