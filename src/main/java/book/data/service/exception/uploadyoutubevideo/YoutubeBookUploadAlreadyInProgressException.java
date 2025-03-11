package book.data.service.exception.uploadyoutubevideo;

public class YoutubeBookUploadAlreadyInProgressException extends RuntimeException {
    public YoutubeBookUploadAlreadyInProgressException(String message) {
        super(message);
    }
}
