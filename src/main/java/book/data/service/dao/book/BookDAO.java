package book.data.service.dao.book;

import java.util.List;
import java.util.Set;

import book.data.service.exception.BookAlreadyExistsException;
import book.data.service.exception.BookDoesNotExistException;
import book.data.service.repository.BookRepository;
import book.data.service.sqlmodel.book.Book;
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

    public List<Book> getAllBooksSortedByBookViewsPaged(int pageNumber, int pageSize,
                                                        String createdBy) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findAllBooksSortedByBookViewsPaged(pageRequest, createdBy);
    }

    public List<Book> getAllBooksSortedByCreationTimePaged(int pageNumber, int pageSize,
                                                           String createdBy) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findAllBooksSortedByCreationTimePaged(pageRequest, createdBy);
    }

    public List<Book> getAllBooksSortedByBookNamePaged(int pageNumber, int pageSize, String createdBy) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findAllBooksSortedByBookNamePaged(pageRequest, createdBy);
    }

    public List<Book> getBooksByBookTagPaged(String bookTag, int pageNumber, int pageSize, String createdBy) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findBooksByBookTagPaged(bookTag, pageRequest, createdBy);
    }

    public Book getBookByBookName(String bookName, String createdBy) {
        log.info("bookName: " + bookName);
        log.info("createdBy: " + createdBy);
        if (!bookRepository.doesBookExist(bookName, createdBy)) {
            throw new BookDoesNotExistException();
        }
        return bookRepository.findBookByBookName(bookName, createdBy);
    }

    public Book getBookByBookNumber(Long bookNumber, String createdBy) {
        if (!bookRepository.doesBookExistWithBookNumber(bookNumber, createdBy)) {
            throw new BookDoesNotExistException();
        }
        return bookRepository.findBookByBookNumber(bookNumber, createdBy);
    }

    public boolean doesBookExistByBookNameOnly(String bookName) {
        return this.bookRepository.doesBookExistWithBookNameOnly(bookName);
    }

    public Book getBookByBookNumberOnly(Long bookNumber) {
        return this.bookRepository.findBookByBookNumberOnly(bookNumber);
    }

    public boolean doesBookExist(String bookName, String createdBy) {
        return this.bookRepository.doesBookExist(bookName, createdBy);
    }

    public Book createBook(
            String bookName,
            String createdBy,
            String bookDescription,
            String bookLanguage,
            Long bookViews,
            String bookThumbnail,
            String bookThumbnailS3Key,
            String bookThumbnailS3Bucket,
            Set<String> bookTags,
            Long createdAtEpochMilli
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
                bookThumbnailS3Key,
                bookThumbnailS3Bucket,
                bookTags,
                createdAtEpochMilli
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
        bookRepository.deleteBook(bookName, createdBy);
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
        bookRepository.updateBookViews(bookName, bookViews, createdBy);
        return bookRepository.findBookByBookName(bookName, createdBy);
    }

    public Book getBookByBookNameOnly(String bookName) {
        List<Book> bookList = bookRepository.findBooksByBookNameOnly(bookName);
        if(bookList.size() > 1) {
            log.error("More than 1 book found for the given name, am just picking one of them");
        }
        return bookList.get(0);
    }

    public Book updateBookViewsByBookNumber(Long bookNumber, Long updatedBookViews, String createdBy) {
        if (!bookRepository.doesBookExistWithBookNumber(bookNumber, createdBy)) {
            throw new BookDoesNotExistException();
        }
        bookRepository.updateBookViewsByBookNumber(bookNumber, updatedBookViews, createdBy);
        Book updatedBook = bookRepository.findBookByBookNumberOnly(bookNumber);
        return updatedBook;
    }
}
