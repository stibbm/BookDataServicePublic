package book.data.service.manager;

import book.data.service.dao.book.BookDAO;
import book.data.service.exception.BookAlreadyExistsException;
import book.data.service.exception.BookDoesNotExistException;
import book.data.service.model.Book;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookManager {

    private BookDAO bookDAO;

    @Autowired
    public BookManager(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<Book> getAllBooksPaged(int pageNumber, int pageSize, String createdBy) {
        return bookDAO.getAllBooksPaged(pageNumber, pageSize, createdBy);
    }

    public Book getBookByBookName(String bookName, String createdBy) throws BookDoesNotExistException {
        return bookDAO.getBookByBookName(bookName, createdBy);
    }

    public Book createBook(
        String bookName,
        String createdBy,
        String bookDescription,
        String bookLanguage,
        Long bookViews,
        String bookThumbnail,
        Set<String> bookTags
    ) throws BookAlreadyExistsException {
        return bookDAO.createBook(
            bookName,
            createdBy,
            bookDescription,
            bookLanguage,
            bookViews,
            bookThumbnail,
            bookTags
        );
    }

}
