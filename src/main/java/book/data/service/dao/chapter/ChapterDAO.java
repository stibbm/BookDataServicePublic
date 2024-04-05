package book.data.service.dao.chapter;

import book.data.service.exception.BookDoesNotExistException;
import book.data.service.exception.ChapterAlreadyExistsException;
import book.data.service.exception.ChapterDoesNotExistException;
import book.data.service.model.Book;
import book.data.service.model.chapter.Chapter;
import book.data.service.model.chapter.ChapterId;
import book.data.service.repository.BookRepository;
import book.data.service.repository.ChapterRepository;
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

    public Chapter createChapter(
        Book book,
        Long chapterNumber,
        String chapterName,
        Long chapterViews,
        String createdBy
    ) {
        if (!bookRepository.doesBookExist(book.getBookName(), createdBy))  {
            throw new BookDoesNotExistException();
        }
        if (chapterRepository.doesChapterExist(book.getBookName(), chapterNumber, createdBy)) {
            throw new ChapterAlreadyExistsException();
        }
        ChapterId chapterId = new ChapterId(
            chapterNumber,
            book
        );
        Chapter chapter = new Chapter(chapterId, chapterName, chapterViews, createdBy);
        chapterRepository.saveChapter(chapter);
        return chapter;
    }
}
