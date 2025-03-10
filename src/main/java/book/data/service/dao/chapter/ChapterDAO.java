package book.data.service.dao.chapter;

import book.data.service.exception.BookDoesNotExistException;
import book.data.service.exception.ChapterAlreadyExistsException;
import book.data.service.repository.BookRepository;
import book.data.service.repository.ChapterRepository;
import book.data.service.sqlmodel.book.Book;
import book.data.service.sqlmodel.chapter.Chapter;
import book.data.service.sqlmodel.chapter.ChapterId;
import book.data.service.sqlmodel.chapter.LockStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
public class ChapterDAO {
    private ChapterRepository chapterRepository;
    private BookRepository bookRepository;

    @Autowired
    public ChapterDAO(
            ChapterRepository chapterRepository,
            BookRepository bookRepository
    ) {
        this.chapterRepository = chapterRepository;
        this.bookRepository = bookRepository;
    }

    public List<Chapter> getAllChaptersPaged(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return chapterRepository.findAllChaptersPaged(pageRequest);
    }

    public List<Chapter> getChaptersByBookNamePaged(String bookName, int pageNumber, int pageSize, String createdBy) {
        if (!bookRepository.doesBookExist(bookName, createdBy)) {
            throw new BookDoesNotExistException();
        }
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return chapterRepository.findChaptersByBookNamePaged(bookName, pageRequest);
    }

    public boolean doesChapterExistByBookNameAndChapterName(String bookName, String chapterName, String createdBy) {
        return chapterRepository.doesChapterExistByBookNameAndChapterName(bookName, chapterName, createdBy);
    }

    public Chapter getChapterByBookNameAndChapterName(String bookName, String chapterName, String createdBy) {
        return chapterRepository.findChapterByBookNameAndChapterName(bookName, chapterName, createdBy);
    }

    public Chapter getChapterByBookNameAndChapterNumber(
            String bookName,
            Long chapterNumber,
            String createdBy
    ) {
        if (!bookRepository.doesBookExist(bookName, createdBy)) {
            throw new BookDoesNotExistException();
        }
        if (!chapterRepository.doesChapterExist(bookName, chapterNumber, createdBy)) {
            throw new ChapterDoesNotExistException();
        }
        return chapterRepository.findChapterByBookNameAndChapterNumber(bookName, chapterNumber, createdBy);
    }

    public Chapter updateChapterLockStatusByBookNumberAndChapterNumber(Long bookNumber, Long chapterNumber, LockStatus lockStatus) {
        chapterRepository.updateLockStatus(bookNumber, chapterNumber, lockStatus);
        Chapter chapter = chapterRepository.findChapterByOnlyBookNumberAndChapterNumber(bookNumber, chapterNumber);
        return chapter;
    }

    public Chapter createChapter(
            Book book,
            Long chapterNumber,
            String chapterName,
            Long chapterViews,
            String createdBy,
            Long createdEpochMilli,
            LockStatus lockStatus
    ) {
        if (!bookRepository.doesBookExist(book.getBookName(), createdBy))  {
            throw new BookDoesNotExistException();
        }
        if (chapterRepository.doesChapterExist(book.getBookName(), chapterNumber, createdBy)) {
            log.info("Book: " + book.toString());
            log.info("Chapter Number: " + chapterNumber.toString());
            log.info("chapterName : " + chapterName);
            log.info("createdBy: " + createdBy.toString());
            log.info("createdEpochMilli: " + createdEpochMilli.toString());
            throw new ChapterAlreadyExistsException();
        }
        ChapterId chapterId = new ChapterId(
                chapterNumber,
                book
        );
        Chapter chapter = new Chapter(chapterId, chapterName, chapterViews, createdBy, createdEpochMilli, lockStatus);
        chapterRepository.saveChapter(chapter);
        return chapter;
    }
}
