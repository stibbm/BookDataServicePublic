package test.book.data.service.manager;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.dao.BookDAOTest.BOOK_DESCRIPTION;
import static test.book.data.service.dao.BookDAOTest.BOOK_LANGUAGE;
import static test.book.data.service.dao.BookDAOTest.BOOK_LIST;
import static test.book.data.service.dao.BookDAOTest.BOOK_NAME;
import static test.book.data.service.dao.BookDAOTest.BOOK_ONE;
import static test.book.data.service.dao.BookDAOTest.BOOK_TAGS;
import static test.book.data.service.dao.BookDAOTest.BOOK_THUMBNAIL;
import static test.book.data.service.dao.BookDAOTest.BOOK_VIEWS;
import static test.book.data.service.dao.BookDAOTest.CREATED_BY_ONE;
import static test.book.data.service.dao.BookDAOTest.PAGE_NUMBER;
import static test.book.data.service.dao.BookDAOTest.PAGE_SIZE;

import book.data.service.dao.book.BookDAO;
import book.data.service.exception.BookAlreadyExistsException;
import book.data.service.exception.BookDoesNotExistException;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BookManagerTest {

    private BookManager bookManager;
    @Mock
    private BookDAO bookDAO;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        bookManager = new BookManager(bookDAO);
    }

    @Test
    public void testGetAllBooksPaged() {
        when(bookDAO.getAllBooksPaged(anyInt(), anyInt(), anyString())).thenReturn(BOOK_LIST);
        List<Book> bookList = bookManager.getAllBooksPaged(PAGE_NUMBER, PAGE_SIZE, CREATED_BY_ONE);
        Assertions.assertThat(bookList).isEqualTo(BOOK_LIST);
    }

    @Test
    public void testGetBookByBookName() {
        when(bookDAO.getBookByBookName(anyString(), anyString())).thenReturn(BOOK_ONE);
        Book book = bookManager.getBookByBookName(BOOK_NAME, CREATED_BY_ONE);
        Assertions.assertThat(book).isEqualTo(BOOK_ONE);
    }

    @Test
    public void testGetBookByName_BookDoesNotExistException() {
        when(bookDAO.getBookByBookName(anyString(), anyString())).thenThrow(
            new BookDoesNotExistException());
        Assert.assertThrows(BookDoesNotExistException.class, () -> {
            bookManager.getBookByBookName(BOOK_NAME, CREATED_BY_ONE);
        });
    }

    @Test
    public void testCreateBook() {
        when(bookDAO.createBook(
            anyString(),
            anyString(),
            anyString(),
            anyString(),
            anyLong(),
            anyString(),
            anySet()
        )).thenReturn(BOOK_ONE);
        Book book = bookManager.createBook(
            BOOK_NAME,
            CREATED_BY_ONE,
            BOOK_DESCRIPTION,
            BOOK_LANGUAGE,
            BOOK_VIEWS,
            BOOK_THUMBNAIL,
            BOOK_TAGS
        );
        Assertions.assertThat(BOOK_ONE).isEqualTo(book);
    }

    @Test
    public void testCreateBook_BookAlreadyExistsException() {
        when(bookDAO.createBook(
            anyString(),
            anyString(),
            anyString(),
            anyString(),
            anyLong(),
            anyString(),
            anySet()
        )).thenThrow(new BookAlreadyExistsException());
        Assert.assertThrows(BookAlreadyExistsException.class, () -> {
            bookManager.createBook(
                BOOK_NAME,
                CREATED_BY_ONE,
                BOOK_DESCRIPTION,
                BOOK_LANGUAGE,
                BOOK_VIEWS,
                BOOK_THUMBNAIL,
                BOOK_TAGS
            );
        });
    }

}

