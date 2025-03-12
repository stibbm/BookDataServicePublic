package book.data.service.exception.audio;

public class AudioAlreadyExistsException extends RuntimeException {
    public AudioAlreadyExistsException(String message) {
        super(message);
    }

    public AudioAlreadyExistsException() {}
}
