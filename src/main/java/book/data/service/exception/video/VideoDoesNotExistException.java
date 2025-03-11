package book.data.service.exception.video;

public class VideoDoesNotExistException extends RuntimeException {
    public VideoDoesNotExistException(String message) {
        super(message);
    }
}
