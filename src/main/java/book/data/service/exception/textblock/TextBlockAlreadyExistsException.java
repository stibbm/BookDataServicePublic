package book.data.service.exception.textblock;

public class TextBlockAlreadyExistsException extends RuntimeException {
    public TextBlockAlreadyExistsException(String message) {
        super(message);
    }

    public TextBlockAlreadyExistsException() {
        
    }
}
