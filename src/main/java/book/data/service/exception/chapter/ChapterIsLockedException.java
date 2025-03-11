package book.data.service.exception.chapter;

public class ChapterIsLockedException extends RuntimeException {
    public ChapterIsLockedException(String message) {
        super(message);
    }
}
