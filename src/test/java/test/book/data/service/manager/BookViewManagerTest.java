package test.book.data.service.manager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.dao.BookDAOTest.BOOK_ONE;
import static test.book.data.service.dao.BookViewDAOTest.BOOK_VIEW_LIST;
import static test.book.data.service.dao.BookViewDAOTest.BOOK_VIEW_NUMBER_ONE;
import static test.book.data.service.dao.BookViewDAOTest.BOOK_VIEW_ONE;
import static test.book.data.service.manager.BookManagerTest.CREATED_AT_EPOCH_MILLI_TIME_ONE;
import static test.book.data.service.manager.BookManagerTest.CREATED_BY_ONE;

import book.data.service.dao.book.BookDAO;
import book.data.service.dao.bookview.BookViewDAO;
import book.data.service.manager.bookview.BookViewManager;
import book.data.service.service.time.TimeService;
import book.data.service.sqlmodel.book.Book;
import book.data.service.sqlmodel.bookview.BookView;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BookViewManagerTest {

    private BookViewManager bookViewManager;
    @Mock
    private BookViewDAO bookViewDAO;
    @Mock
    private BookDAO bookDAO;
    @Mock
    private TimeService timeService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        bookViewManager = new BookViewManager(bookViewDAO, bookDAO, timeService);
    }

    @Test
    public void testGetBookViewsByBookNumber() {
        when(bookViewDAO.getBookViewsByBookNumber(anyLong(), anyString()))
            .thenReturn(BOOK_VIEW_LIST);
        List<BookView> bookViewList = bookViewManager.getBookViewsByBookNumber(
            BOOK_VIEW_NUMBER_ONE, CREATED_BY_ONE);
        Assertions.assertThat(bookViewList).isEqualTo(BOOK_VIEW_LIST);
    }

    @Test
    public void testCreateBookView() {
        when(bookViewDAO.createBookView(
            any(Book.class),
            anyString(),
            anyLong())).thenReturn(BOOK_VIEW_ONE);
        when(timeService.getCurrentLocalDateTimeLong()).thenReturn(CREATED_AT_EPOCH_MILLI_TIME_ONE);
        when(bookDAO.getBookByBookNumber(anyLong(), anyString()))
            .thenReturn(BOOK_ONE);
        BookView bookViewOne = bookViewManager.createBookView(
            anyLong(),
            anyString()
        );

        bookViewOne.setBookViewNumber(BOOK_VIEW_ONE.getBookViewNumber());
        Assertions.assertThat(bookViewOne).isEqualTo(BOOK_VIEW_ONE);
    }
}
