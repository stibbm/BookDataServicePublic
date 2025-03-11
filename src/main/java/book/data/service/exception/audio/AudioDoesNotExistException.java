package book.data.service.exception.audio;

public class AudioDoesNotExistException extends RuntimeException {
    public AudioDoesNotExistException(String message) {
        super(message);
    }
}
