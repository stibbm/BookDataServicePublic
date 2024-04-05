package book.data.service.dao.book;

import book.data.service.exception.BookAlreadyExistsException;
import book.data.service.exception.BookDoesNotExistException;
import java.util.List;
import book.data.service.model.Book;
import book.data.service.repository.BookRepository;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookDAO {

    private BookRepository bookRepository;

    @Autowired
    public BookDAO(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooksPaged(int pageNumber, int pageSize, String createdBy) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findAllBooksPaged(pageRequest, createdBy);
    }

    public Book getBookByBookName(String bookName, String createdBy) {
        if (!bookRepository.doesBookExist(bookName, createdBy)) {
            throw new BookDoesNotExistException();
        }
        return bookRepository.findBookByBookName(bookName, createdBy);
    }

    public Book createBook(
        String bookName,
        String createdBy,
        String bookDescription,
        String bookLanguage,
        Long bookViews,
        String bookThumbnail,
        Set<String> bookTags
    ) {
        if (bookRepository.doesBookExist(bookName, createdBy)) {
            throw new BookAlreadyExistsException();
        }
        Book book = Book.createBookWithoutBookId(
            bookName,
            createdBy,
            bookDescription,
            bookLanguage,
            bookViews,
            bookThumbnail,
            bookTags
        );
        bookRepository.saveBook(book);
        return book;
    }

    public Book updateThumbnail(String bookName, String updatedBookThumbnail, String createdBy) {
        if (!bookRepository.doesBookExist(bookName, createdBy)) {
            throw new BookDoesNotExistException();
        }
        bookRepository.updateBookThumbnail(bookName, updatedBookThumbnail);
        Book updatedBook = bookRepository.findBookByBookName(bookName, createdBy);
        return updatedBook;
    }

    public Book deleteBook(String bookName, String createdBy) {
        if (!bookRepository.doesBookExist(bookName, createdBy)) {
            throw new BookDoesNotExistException();
        }
        Book book = bookRepository.findBookByBookName(bookName, createdBy);
        bookRepository.deleteBook(bookName);
        return book;
    }

    public Book updateBookViews(
        String bookName,
        Long bookViews,
        String createdBy
    ) {
        if (!bookRepository.doesBookExist(bookName, createdBy)) {
            throw new BookDoesNotExistException();
        }
        bookRepository.updateBookViews(bookName, bookViews);
        return bookRepository.findBookByBookName(bookName, createdBy);
    }
}
