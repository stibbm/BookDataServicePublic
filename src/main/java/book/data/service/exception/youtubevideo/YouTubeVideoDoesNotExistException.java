package book.data.service.exception.youtubevideo;

public class YouTubeVideoDoesNotExistException extends RuntimeException {
    public YouTubeVideoDoesNotExistException(String message) {
        super(message);
    }

    public YouTubeVideoDoesNotExistException() {}
}
