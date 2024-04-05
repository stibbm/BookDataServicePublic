package book.data.service.manager;

import book.data.service.dao.book.BookDAO;
import book.data.service.dao.chapter.ChapterDAO;
import book.data.service.exception.BookDoesNotExistException;
import book.data.service.exception.ChapterAlreadyExistsException;
import book.data.service.exception.ChapterDoesNotExistException;
import book.data.service.model.Book;
import book.data.service.model.chapter.Chapter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChapterManager {
    private ChapterDAO chapterDAO;
    private BookDAO bookDAO;

    @Autowired
    public ChapterManager(
        ChapterDAO chapterDAO,
        BookDAO bookDAO
    ) {
        this.chapterDAO = chapterDAO;
        this.bookDAO = bookDAO;
    }

    public List<Chapter> getAllChaptersPaged(int pageNumber, int pageSize) {
        return chapterDAO.getAllChaptersPaged(pageNumber, pageSize);
    }

    public List<Chapter> getChaptersByBookNamePaged(
        String bookName,
        int pageNumber,
        int pageSize,
        String createdBy
    ) throws BookDoesNotExistException {
        return chapterDAO.getChaptersByBookNamePaged(bookName, pageNumber, pageSize, createdBy);
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

    public Chapter createChapter(
        String bookName,
        Long chapterNumber,
        String chapterName,
        Long chapterViews,
        String createdBy
    ) throws BookDoesNotExistException, ChapterAlreadyExistsException {
        Book book = bookDAO.getBookByBookName(bookName, createdBy);
        return chapterDAO.createChapter(
            book, chapterNumber, chapterName, chapterViews, createdBy
        );
    }

}
