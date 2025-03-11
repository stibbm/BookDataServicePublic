package book.data.service.manager.chapter;


import java.util.List;

import book.data.service.dao.book.BookDAO;
import book.data.service.dao.chapter.ChapterDAO;
import book.data.service.exception.book.BookDoesNotExistException;
import book.data.service.exception.chapter.ChapterAlreadyExistsException;
import book.data.service.exception.chapter.ChapterDoesNotExistException;
import book.data.service.sqlmodel.book.Book;
import book.data.service.sqlmodel.chapter.Chapter;
import book.data.service.sqlmodel.chapter.LockStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChapterManager {
    private ChapterDAO chapterDAO;
    private BookDAO bookDAO;
    private TimeService timeService;

    @Autowired
    public ChapterManager(
            ChapterDAO chapterDAO,
            BookDAO bookDAO,
            TimeService timeService
    ) {
        this.chapterDAO = chapterDAO;
        this.bookDAO = bookDAO;
        this.timeService = timeService;
    }

    public List<Chapter> getAllChaptersPaged(int pageNumber, int pageSize) {
        return chapterDAO.getAllChaptersPaged(pageNumber, pageSize);
    }

    public boolean doesChapterExistByBookNameAndChapterName(String bookName,String chapterName, String createdBy) {
        return chapterDAO.doesChapterExistByBookNameAndChapterName(bookName, chapterName, createdBy);
    }

    public List<Chapter> getChaptersByBookNamePaged(
            String bookName,
            int pageNumber,
            int pageSize,
            String createdBy
    ) throws BookDoesNotExistException {
        return chapterDAO.getChaptersByBookNamePaged(bookName, pageNumber, pageSize, createdBy);
    }

    public Chapter getChapterByBookNameAndChapterName(String bookName, String chapterName, String createdBy) {
        Chapter chapter = chapterDAO.getChapterByBookNameAndChapterName(bookName, chapterName, createdBy);
        return chapter;
    }

    public Chapter getChapterByBookNameAndChapterNumber(
            String bookName,
            Long chapterNumber,
            String createdBy
    ) throws BookDoesNotExistException, ChapterDoesNotExistException {
        return chapterDAO.getChapterByBookNameAndChapterNumber(
                bookName,
                chapterNumber,
                createdBy
        );
    }

    public Chapter updateChapterLockStatus(
            Long bookNumber, Long chapterNumber, LockStatus lockStatus
    ) {
        Chapter chapter = chapterDAO.updateChapterLockStatusByBookNumberAndChapterNumber(
                bookNumber, chapterNumber, lockStatus
        );
        return chapter;
    }

    public Chapter createChapterNoViews(
            String bookName,
            Long chapterNumber,
            String chapterName,
            String createdBy
    ) {
        Chapter chapter = createChapter(
                bookName,
                chapterNumber,
                chapterName,
                NO_VIEWS,
                createdBy
        );
        return chapter;
    }

    public Chapter createChapter(
            String bookName,
            Long chapterNumber,
            String chapterName,
            Long chapterViews,
            String createdBy
    ) throws BookDoesNotExistException, ChapterAlreadyExistsException {
        log.info("bookName: " + bookName);
        log.info("chapterName: " + chapterName);
        log.info("chapterNumber: " + chapterNumber);
        log.info("chapterViews: " + chapterViews);
        log.info("createdBy: " + createdBy);
        Book book = bookDAO.getBookByBookName(bookName, createdBy);
        Long createdEpochMilli = timeService.getCurrentLocalDateTimeLong();
        return chapterDAO.createChapter(
                book, chapterNumber, chapterName, chapterViews, createdBy, createdEpochMilli,
                LockStatus.EDITABLE
        );
    }

}
