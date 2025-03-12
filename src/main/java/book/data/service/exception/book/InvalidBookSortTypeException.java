package book.data.service.exception.book;

public class InvalidBookSortTypeException extends RuntimeException {
    public InvalidBookSortTypeException(String message) {
        super(message);
    }

    public InvalidBookSortTypeException() {}
}
