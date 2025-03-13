package test.book.data.service.dao;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.activity.image.GetImagesByBookNameAndChapterNumberPagedActivityTest.CREATED_BY_ONE;
import static test.book.data.service.dao.BookDAOTest.BOOK_NUMBER_ONE;

import book.data.service.dao.bookview.BookViewDAO;
import book.data.service.exception.book.BookDoesNotExistException;
import book.data.service.repository.BookRepository;
import book.data.service.repository.BookViewRepository;
import book.data.service.sqlmodel.bookview.BookView;
import com.google.common.collect.ImmutableList;
import java.time.Instant;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import test.book.data.service.activity.image.GetImagesByBookNameAndChapterNumberPagedActivityTest;

public class BookViewDAOTest {

    public static final Long BOOK_VIEW_NUMBER_ONE = 1L;
    public static final Long BOOK_VIEW_NUMBER_TWO = 2L;
    public static final Long CREATED_AT_EPOCH_MILLI_TIME_ONE = Instant.now().toEpochMilli();
    public static final Long CREATED_AT_EPOCH_MILLI_TIME_TWO = Instant.now().toEpochMilli();

    public static final BookView BOOK_VIEW_ONE = BookView.builder()
        .bookViewNumber(BOOK_VIEW_NUMBER_TWO)
        .bookNumber(BOOK_NUMBER_ONE)
        .createdBy(CREATED_BY_ONE)
        .createdEpochMilli(CREATED_AT_EPOCH_MILLI_TIME_ONE)
        .build();
    public static final BookView BOOK_VIEW_TWO = BookView.builder()
        .bookViewNumber(BOOK_VIEW_NUMBER_TWO)
        .bookNumber(BOOK_NUMBER_ONE)
        .createdBy(
            GetImagesByBookNameAndChapterNumberPagedActivityTest.CREATED_BY_TWO)
        .createdEpochMilli(CREATED_AT_EPOCH_MILLI_TIME_TWO)
        .build();

    public static final List<BookView> BOOK_VIEW_LIST = ImmutableList.of(BOOK_VIEW_ONE,
        BOOK_VIEW_TWO);

    private BookViewDAO bookViewDAO;
    @Mock
    private BookViewRepository bookViewRepository;
    @Mock
    private BookRepository bookRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        bookViewDAO = new BookViewDAO(bookViewRepository, bookRepository);
    }

    @Test
    public void testGetBookViewsByBookNumber() {
        when(bookRepository.doesBookExistWithBookNumber(anyLong(), anyString()))
            .thenReturn(true);
        when(bookViewRepository.findAllBookViewsByBookNumber(anyLong()))
            .thenReturn(BOOK_VIEW_LIST);
        List<BookView> bookViewList = bookViewDAO.getBookViewsByBookNumber(BOOK_NUMBER_ONE,
            CREATED_BY_ONE);
        Assertions.assertThat(bookViewList).isEqualTo(BOOK_VIEW_LIST);
    }

    @Test
    public void testGetBookViewsByBookNumber_BookDoesNotExistException() {
        when(bookRepository.doesBookExistWithBookNumber(anyLong(), anyString()))
            .thenReturn(false);
        when(bookViewRepository.findAllBookViewsByBookNumber(anyLong()))
            .thenReturn(BOOK_VIEW_LIST);
        Assert.assertThrows(BookDoesNotExistException.class, () -> {
            bookViewDAO.getBookViewsByBookNumber(
                BOOK_NUMBER_ONE,
                CREATED_BY_ONE
            );
        });
    }

    @Test
    public void testCreateBookView() {
        BookView bookView = bookViewDAO.createBookView(
            BookDAOTest.BOOK_ONE,
            CREATED_BY_ONE,
            CREATED_AT_EPOCH_MILLI_TIME_ONE
        );
        bookView.setBookViewNumber(BOOK_VIEW_ONE.getBookViewNumber());
        Assertions.assertThat(bookView).isEqualTo(BOOK_VIEW_ONE);
    }

    @Test
    public void testCreateBookView_CreatedByNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> {
            bookViewDAO.createBookView(
                BookDAOTest.BOOK_ONE,
                null,
                CREATED_AT_EPOCH_MILLI_TIME_ONE
            );
        });
    }

}
