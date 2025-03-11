package book.data.service.exception.video;

public class VideoAlreadyExistsException extends RuntimeException {
  public VideoAlreadyExistsException(String message) {
    super(message);
  }
}
