package test.book.data.service.manager;

import book.data.service.clientwrapper.S3ClientWrapper;
import book.data.service.constants.BookSortTypes;
import book.data.service.dao.book.BookDAO;
import book.data.service.dao.textblock.TextBlockDAO;
import book.data.service.exception.book.BookAlreadyExistsException;
import book.data.service.exception.book.BookDoesNotExistException;
import book.data.service.manager.book.BookManager;
import book.data.service.service.filter.BookFilterService;
import book.data.service.service.id.IdService;
import book.data.service.service.time.TimeService;
import book.data.service.sqlmodel.book.Book;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;

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
import static test.book.data.service.dao.BookDAOTest.PAGE_NUMBER;
import static test.book.data.service.dao.BookDAOTest.PAGE_SIZE;
import static test.book.data.service.dao.ImageDAOTest.RELATIVE_IMAGE_URL_ONE;

public class BookManagerTest {

    public static final Long CREATED_AT_EPOCH_MILLI_TIME_ONE = Instant.now().toEpochMilli();
    public static final Long CREATED_AT_EPOCH_MILLI_TIME_TWO = Instant.now().toEpochMilli();
    public static final String CREATED_BY_ONE = "testCreatedByOne@gmail.com";
    public static final String CREATED_BY_TWO = "testCreatedByTwo@gmail.com";
    public static byte[] BYTES_ONE = "BytesOne".getBytes();
    public static final String FILE_TYPE_ONE = ".jpg";

    private BookManager bookManager;
    @Mock
    private BookDAO bookDAO;
    @Mock
    private S3ClientWrapper s3ClientWrapper;
    @Mock
    private IdService idService;
    @Mock
    private TimeService timeService;
    @Mock
    private BookFilterService bookFilterService;
    @Mock
    private TextBlockDAO textBlockDAO;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        bookManager = new BookManager(bookDAO, s3ClientWrapper, idService, timeService,
                bookFilterService, textBlockDAO);
    }

    @Test
    public void testGetAllBooksPaged() throws MalformedURLException, URISyntaxException {
        when(idService.generateThumbnailS3Key(anyString(), anyString(), anyString()))
                .thenReturn(BOOK_THUMBNAIL);
        when(idService.generateRelativeImageUrlFromS3Key(anyString()))
                .thenReturn(RELATIVE_IMAGE_URL_ONE);
        when(bookDAO.getAllBooksPaged(anyInt(), anyInt(), anyString())).thenReturn(BOOK_LIST);
        List<Book> bookList = bookManager.getAllBooksPaged(PAGE_NUMBER, PAGE_SIZE, CREATED_BY_ONE);
        Assertions.assertThat(bookList).isEqualTo(BOOK_LIST);
    }

    @Test
    public void testGetAllBooksSortedByBookViewsPaged() {
        when(bookDAO.getAllBooksSortedByBookViewsPaged(anyInt(), anyInt(), anyString()))
                .thenReturn(BOOK_LIST);
        List<Book> bookList = bookManager.getAllBooksSortedPaged(BookSortTypes.BOOK_VIEWS,
                PAGE_NUMBER, PAGE_SIZE, CREATED_BY_ONE);
        Assertions.assertThat(bookList).isEqualTo(BOOK_LIST);
    }

    @Test
    public void testGetAllBooksSortedByCreationTimePaged() {
        when(bookDAO.getAllBooksSortedByCreationTimePaged(anyInt(), anyInt(), anyString()))
                .thenReturn(BOOK_LIST);
        List<Book> bookList = bookManager.getAllBooksSortedPaged(BookSortTypes.CREATION_TIME,
                PAGE_NUMBER, PAGE_SIZE, CREATED_BY_ONE);
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
    public void testCreateBook() throws MalformedURLException, URISyntaxException {
        when(idService.generateThumbnailS3Key(anyString(), anyString(), anyString()))
                .thenReturn(BOOK_THUMBNAIL);
        when(idService.generateRelativeImageUrlFromS3Key(anyString()))
                .thenReturn(RELATIVE_IMAGE_URL_ONE);
        when(timeService.getCurrentLocalDateTimeLong()).thenReturn(CREATED_AT_EPOCH_MILLI_TIME_ONE);
        when(bookDAO.createBook(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyLong(),
                anyString(),
                anyString(),
                anyString(),
                anySet(),
                anyLong()
        )).thenReturn(BOOK_ONE);
        Book book = bookManager.createBook(
                BOOK_NAME,
                CREATED_BY_ONE,
                BOOK_DESCRIPTION,
                BOOK_LANGUAGE,
                BOOK_VIEWS,
                BOOK_TAGS,
                BYTES_ONE,
                FILE_TYPE_ONE
        );
        Assertions.assertThat(BOOK_ONE).isEqualTo(book);
    }

    @Test
    public void testCreateBook_BookAlreadyExistsException()
            throws MalformedURLException, URISyntaxException {
        when(timeService.getCurrentLocalDateTimeLong()).thenReturn(CREATED_AT_EPOCH_MILLI_TIME_ONE);
        when(bookDAO.createBook(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyLong(),
                anyString(),
                anyString(),
                anyString(),
                anySet(),
                anyLong()
        )).thenThrow(new BookAlreadyExistsException());
        when(idService.generateThumbnailS3Key(anyString(), anyString(), anyString()))
                .thenReturn(BOOK_THUMBNAIL);
        when(idService.generateRelativeImageUrlFromS3Key(anyString()))
                .thenReturn(RELATIVE_IMAGE_URL_ONE);
        Assert.assertThrows(BookAlreadyExistsException.class, () -> {
            bookManager.createBook(
                    BOOK_NAME,
                    CREATED_BY_ONE,
                    BOOK_DESCRIPTION,
                    BOOK_LANGUAGE,
                    BOOK_VIEWS,
                    BOOK_TAGS,
                    BYTES_ONE,
                    FILE_TYPE_ONE
            );
        });
    }

}
