package book.data.service.dao.bookview;

import book.data.service.exception.book.BookDoesNotExistException;
import book.data.service.repository.BookRepository;
import book.data.service.repository.BookViewRepository;
import book.data.service.sqlmodel.book.Book;
import book.data.service.sqlmodel.bookview.BookView;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.utils.Validate;

@Slf4j
@Service
public class BookViewDAO {

    private BookViewRepository bookViewRepository;
    private BookRepository bookRepository;

    @Autowired
    public BookViewDAO(BookViewRepository bookViewRepository, BookRepository bookRepository) {
        this.bookViewRepository = bookViewRepository;
        this.bookRepository = bookRepository;
    }

    // checks for matching the createdBy when doing the doesBookExist
    public List<BookView> getBookViewsByBookNumber(Long bookNumber, String createdBy) {
        Validate.notNull(bookNumber, "bookNumber is required");
        Validate.notNull(createdBy, "createdBy is required");
        if (!bookRepository.doesBookExistWithBookNumber(bookNumber, createdBy)) {
            throw new BookDoesNotExistException();
        }
        return bookViewRepository.findAllBookViewsByBookNumber(bookNumber);
    }

    public BookView createBookView(
        Book book,
        String createdBy,
        Long createdEpochMilli
    ) {
        Validate.notNull(book, "book is required");
        Validate.notNull(createdBy, "createdBy is required");
        Validate.notNull(createdEpochMilli, "createdEpochMilli is required");
        BookView bookView = BookView.builder()
            .bookNumber(book.getBookNumber())
            .createdBy(createdBy)
            .createdEpochMilli(createdEpochMilli)
            .build();
        bookViewRepository.saveBookView(bookView);
        return bookView;
    }
}
