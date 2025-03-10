package test.book.data.service.dao;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import book.data.service.sqlmodel.book.Book;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

public class BookDAOTest {
    public static final Long BOOK_NUMBER_ONE = 1L;
    public static final Long BOOK_NUMBER_TWO = 2L;
    public static final String BOOK_NAME = "survmaget";
    public static final String BOOK_NAME_TWO = "bookNameTwo";
    public static final String BOOK_DESCRIPTION = "bookdescriptiontest";
    public static final String BOOK_DESCRIPTION_TWO = "bookDescriptionTwo";
    public static final String BOOK_LANGUAGE = "English";
    public static final String BOOK_LANGUAGE_TWO = "Chinese";
    public static final Long BOOK_VIEWS = 1000L;
    public static final Long BOOK_VIEWS_TWO = 2000L;
    public static final String BOOK_THUMBNAIL = "bookThumbnailTest";
    public static final String BOOK_THUMBNAIL_TWO = "bookThumbnailTwoTest";
    public static final String BOOK_THUMBNAIL_S3_KEY_ONE = "s3KeyOne";
    public static final String BOOK_THUMBNAIL_S3_KEY_TWO = "s3KeyTwo";
    public static final String BOOK_THUMBNAIL_S3_BUCKET_ONE = "s3BucketOne";
    public static final String BOOK_THUMBNAIL_S3_BUCKET_TWO = "s3BucketTwo";
    public static final String BOOK_TAG_ONE = "Tag1";
    public static final Set<String> BOOK_TAGS = ImmutableSet.of("Tag1","Tag2");
    public static final Set<String> BOOK_TAGS_TWO = ImmutableSet.of("Tag3", "Tag4");

    public static final int PAGE_SIZE = 100;
    public static final int PAGE_NUMBER = 1;

    public static final Book BOOK_ONE = new Book(
            BOOK_NUMBER_ONE,
            BOOK_NAME,
            CREATED_BY_ONE,
            BOOK_DESCRIPTION,
            BOOK_LANGUAGE,
            BOOK_VIEWS,
            BOOK_THUMBNAIL,
            BOOK_THUMBNAIL_S3_KEY_ONE,
            BOOK_THUMBNAIL_S3_BUCKET_ONE,
            BOOK_TAGS,
            CREATED_AT_EPOCH_MILLI_TIME_ONE
    );
    public static final Book EXPECTED_BOOK_ONE = new Book(
            BOOK_NUMBER_ONE,
            BOOK_NAME,
            CREATED_BY_ONE,
            BOOK_DESCRIPTION,
            BOOK_LANGUAGE,
            BOOK_VIEWS,
            BOOK_THUMBNAIL,
            BOOK_THUMBNAIL_S3_KEY_ONE,
            BOOK_THUMBNAIL_S3_BUCKET_ONE,
            ImmutableSet.of("Tag1","Tag2"),
            CREATED_AT_EPOCH_MILLI_TIME_ONE
    );
    public static final Book EXPECTED_BOOK_TWO = new Book(
            BOOK_NUMBER_TWO,
            BOOK_NAME_TWO,
            CREATED_BY_TWO,
            BOOK_DESCRIPTION_TWO,
            BOOK_LANGUAGE_TWO,
            BOOK_VIEWS_TWO,
            BOOK_THUMBNAIL_TWO,
            BOOK_THUMBNAIL_S3_KEY_ONE,
            BOOK_THUMBNAIL_S3_BUCKET_ONE,
            ImmutableSet.of("Tag3", "Tag4"),
            CREATED_AT_EPOCH_MILLI_TIME_TWO
    );
    public static final Book BOOK_TWO = new Book(
            BOOK_NUMBER_TWO,
            BOOK_NAME_TWO,
            CREATED_BY_TWO,
            BOOK_DESCRIPTION_TWO,
            BOOK_LANGUAGE_TWO,
            BOOK_VIEWS_TWO,
            BOOK_THUMBNAIL_TWO,
            BOOK_THUMBNAIL_S3_KEY_ONE,
            BOOK_THUMBNAIL_S3_BUCKET_ONE,
            BOOK_TAGS_TWO,
            CREATED_AT_EPOCH_MILLI_TIME_TWO
    );
    public static final List<Book> BOOK_LIST = ImmutableList.of(
            BOOK_ONE, BOOK_TWO
    );
    public static final List<Book> EXPECTED_BOOK_LIST = ImmutableList.of(
            EXPECTED_BOOK_ONE, EXPECTED_BOOK_TWO
    );

    private BookDAO bookDAO;

    @Mock
    private BookRepository bookRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        bookDAO = new BookDAO(bookRepository);
    }

    @Test
    public void testGetAllBooksPaged() {
        when(bookRepository.findAllBooksPaged(any(PageRequest.class), anyString()))
                .thenReturn(BOOK_LIST);
        List<Book> booksList = bookDAO.getAllBooksPaged(PAGE_NUMBER, PAGE_SIZE, CREATED_BY_ONE);
        Assertions.assertThat(booksList).isEqualTo(EXPECTED_BOOK_LIST);
    }

    @Test
    public void testGetAllBooksSortedByBookViewsPaged() {
        when(bookRepository.findAllBooksSortedByBookViewsPaged(any(PageRequest.class), anyString()))
                .thenReturn(BOOK_LIST);
        List<Book> bookList = bookDAO.getAllBooksSortedByBookViewsPaged(PAGE_NUMBER, PAGE_SIZE, CREATED_BY_ONE);
        Assertions.assertThat(bookList).isEqualTo(EXPECTED_BOOK_LIST);
    }

    @Test
    public void testGetAllBooksSortedByCreationTimePaged() {
        when(bookRepository.findAllBooksSortedByCreationTimePaged(any(PageRequest.class), anyString()))
                .thenReturn(BOOK_LIST);
        List<Book> bookList = bookDAO.getAllBooksSortedByCreationTimePaged(PAGE_NUMBER, PAGE_SIZE, CREATED_BY_ONE);
        Assertions.assertThat(bookList).isEqualTo(EXPECTED_BOOK_LIST);
    }

    @Test
    public void testGetAllBooksSortedByBookNamePaged() {
        when(bookRepository.findAllBooksSortedByBookNamePaged(any(PageRequest.class), anyString()))
                .thenReturn(BOOK_LIST);
        List<Book> bookList = bookDAO.getAllBooksSortedByBookNamePaged(PAGE_NUMBER, PAGE_SIZE, CREATED_BY_ONE);
        Assertions.assertThat(bookList).isEqualTo(EXPECTED_BOOK_LIST);
    }

    @Test
    public void testGetBooksByBookTagPaged() {
        when(bookRepository.findBooksByBookTagPaged(anyString(), any(PageRequest.class), anyString()))
                .thenReturn(BOOK_LIST);
        List<Book> bookList = bookDAO.getBooksByBookTagPaged(BOOK_TAG_ONE, PAGE_NUMBER, PAGE_SIZE, CREATED_BY_ONE);
        Assertions.assertThat(bookList).isEqualTo(BOOK_LIST);
    }

    @Test
    public void testGetBookByBookName() {
        when(bookRepository.doesBookExist(BOOK_NAME, CREATED_BY_ONE)).thenReturn(true);
        when(bookRepository.findBookByBookName(BOOK_NAME, CREATED_BY_ONE)).thenReturn(BOOK_ONE);
        Book book = bookDAO.getBookByBookName(BOOK_NAME, CREATED_BY_ONE);
        Assertions.assertThat(book).isEqualTo(BOOK_ONE);
    }

    @Test
    public void testGetBookByBookNumber() {
        when(bookRepository.doesBookExistWithBookNumber(BOOK_NUMBER_ONE, CREATED_BY_ONE)).thenReturn(true);
        when(bookRepository.findBookByBookNumberOnly(BOOK_NUMBER_ONE)).thenReturn(BOOK_ONE);
        when(bookRepository.findBookByBookNumber(BOOK_NUMBER_ONE, CREATED_BY_ONE)).thenReturn(BOOK_ONE);
        Book book = bookDAO.getBookByBookNumber(BOOK_NUMBER_ONE, CREATED_BY_ONE);
        Assertions.assertThat(book).isEqualTo(BOOK_ONE);
    }

    @Test
    public void testGetBookByBookName_BookDoesNotExistException() {
        when(bookRepository.doesBookExist(anyString(), anyString())).thenThrow(new BookDoesNotExistException());
        Assert.assertThrows(BookDoesNotExistException.class, () -> {
            bookDAO.getBookByBookName(BOOK_NAME, CREATED_BY_ONE);
        });
    }

    @Test
    public void testCreateBook() {
        when(bookRepository.doesBookExist(BOOK_NAME, CREATED_BY_ONE)).thenReturn(false);
        Book book = bookDAO.createBook(
                BOOK_NAME,
                CREATED_BY_ONE,
                BOOK_DESCRIPTION,
                BOOK_LANGUAGE,
                BOOK_VIEWS,
                BOOK_THUMBNAIL,
                BOOK_THUMBNAIL_S3_KEY_ONE,
                BOOK_THUMBNAIL_S3_BUCKET_ONE,
                BOOK_TAGS,
                CREATED_AT_EPOCH_MILLI_TIME_ONE
        );
        book.setBookNumber(BOOK_ONE.getBookNumber());
        Assertions.assertThat(book).isEqualTo(BOOK_ONE);
    }

    @Test
    public void testCreateBook_BookAlreadyExistsException() {
        when(bookRepository.doesBookExist(BOOK_NAME, CREATED_BY_ONE)).thenReturn(true);
        Assert.assertThrows(BookAlreadyExistsException.class, () -> {
            bookDAO.createBook(
                    BOOK_NAME,
                    CREATED_BY_ONE,
                    BOOK_DESCRIPTION,
                    BOOK_LANGUAGE,
                    BOOK_VIEWS,
                    BOOK_THUMBNAIL,
                    BOOK_THUMBNAIL_S3_KEY_ONE,
                    BOOK_THUMBNAIL_S3_BUCKET_ONE,
                    BOOK_TAGS,
                    CREATED_AT_EPOCH_MILLI_TIME_ONE
            );
        });
    }

    @Test
    public void testUpdateBookThumbnail() {
        when(bookRepository.doesBookExist(BOOK_NAME, CREATED_BY_ONE)).thenReturn(true);
        when(bookRepository.findBookByBookName(BOOK_NAME, CREATED_BY_ONE)).thenReturn(BOOK_ONE);
        Book book = bookDAO.updateThumbnail(BOOK_NAME, BOOK_THUMBNAIL_TWO, CREATED_BY_ONE);
        Assertions.assertThat(book).isEqualTo(BOOK_ONE);
    }

    @Test
    public void testUpdateBookThumbnail_BookDoesNotExistException() {
        when(bookRepository.doesBookExist(BOOK_NAME, CREATED_BY_ONE)).thenReturn(false);
        Assert.assertThrows(BookDoesNotExistException.class, () -> {
            bookDAO.updateThumbnail(BOOK_NAME, BOOK_THUMBNAIL_TWO, CREATED_BY_ONE);
        });
    }

    @Test
    public void testDeleteBook() {
        when(bookRepository.doesBookExist(BOOK_NAME, CREATED_BY_ONE)).thenReturn(true);
        when(bookRepository.findBookByBookName(BOOK_NAME, CREATED_BY_ONE)).thenReturn(BOOK_ONE);
        Book book = bookDAO.deleteBook(BOOK_NAME, CREATED_BY_ONE);
        Assertions.assertThat(book).isEqualTo(BOOK_ONE);
    }

}
