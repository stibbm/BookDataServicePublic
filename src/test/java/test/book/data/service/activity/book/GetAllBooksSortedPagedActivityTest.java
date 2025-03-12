package test.book.data.service.activity.book;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.activity.book.GetAllBooksPagedActivityTest.AUTH_TOKEN;
import static test.book.data.service.dao.BookDAOTest.BOOK_LIST;
import static test.book.data.service.dao.BookDAOTest.PAGE_NUMBER;
import static test.book.data.service.dao.BookDAOTest.PAGE_SIZE;
import static test.book.data.service.manager.BookManagerTest.CREATED_BY_ONE;

import book.data.service.activity.book.GetAllBooksSortedPagedActivity;
import book.data.service.constants.BookSortTypes;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.book.BookManager;
import book.data.service.message.book.allbookssortedpaged.GetAllBooksSortedPagedRequest;
import book.data.service.message.book.allbookssortedpaged.GetAllBooksSortedPagedResponse;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
public class GetAllBooksSortedPagedActivityTest {

    public static final GetAllBooksSortedPagedRequest GET_ALL_BOOKS_SORTED_BY_BOOK_VIEWS_PAGED_REQUEST =
        GetAllBooksSortedPagedRequest.builder()
            .sortType(BookSortTypes.BOOK_VIEWS)
            .pageNumber("" + PAGE_NUMBER)
            .pageSize("" + PAGE_SIZE)
            .build();
    public static final GetAllBooksSortedPagedResponse GET_ALL_BOOKS_SORTED_BY_BOOK_VIEWS_PAGED_RESPONSE =
        GetAllBooksSortedPagedResponse.builder()
            .sortType(BookSortTypes.BOOK_VIEWS)
            .bookList(BOOK_LIST)
            .build();
    public static final GetAllBooksSortedPagedRequest GET_ALL_BOOKS_SORTED_BY_CREATED_TIME_PAGED_REQUEST =
        GetAllBooksSortedPagedRequest.builder()
            .sortType(BookSortTypes.CREATION_TIME)
            .pageNumber("" + PAGE_NUMBER)
            .pageSize("" + PAGE_SIZE)
            .build();
    public static final GetAllBooksSortedPagedResponse GET_ALL_BOOKS_SORTED_BY_CREATED_TIME_PAGED_RESPONSE =
        GetAllBooksSortedPagedResponse.builder()
            .sortType(BookSortTypes.CREATION_TIME)
            .bookList(BOOK_LIST)
            .build();
    private GetAllBooksSortedPagedActivity getAllBooksSortedPagedActivity;
    @Mock
    private BookManager bookManager;
    @Mock
    private FirebaseService firebaseService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.getAllBooksSortedPagedActivity = new GetAllBooksSortedPagedActivity(
            bookManager, firebaseService
        );
    }

    @Test
    public void testGetAllBooksSortedByBookViews() {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(bookManager.getAllBooksSortedPaged(
            BookSortTypes.BOOK_VIEWS,
            (PAGE_NUMBER),
            (PAGE_SIZE),
            CREATED_BY_ONE)).thenReturn(BOOK_LIST);
        GetAllBooksSortedPagedResponse getAllBooksSortedPagedResponse = getAllBooksSortedPagedActivity.getAllBooksSortedPaged(
            GET_ALL_BOOKS_SORTED_BY_BOOK_VIEWS_PAGED_REQUEST,
            AUTH_TOKEN);
        Assertions.assertThat(getAllBooksSortedPagedResponse).isEqualTo(
            GET_ALL_BOOKS_SORTED_BY_BOOK_VIEWS_PAGED_RESPONSE);
    }

    @Test
    public void testGetAllBooksSortedByCreationTime() {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(bookManager.getAllBooksSortedPaged(
            BookSortTypes.CREATION_TIME,
            PAGE_NUMBER,
            PAGE_SIZE,
            CREATED_BY_ONE
        )).thenReturn(BOOK_LIST);
        GetAllBooksSortedPagedResponse getAllBooksSortedPagedResponse = getAllBooksSortedPagedActivity.getAllBooksSortedPaged(
            GET_ALL_BOOKS_SORTED_BY_CREATED_TIME_PAGED_REQUEST,
            AUTH_TOKEN
        );
        log.info("getAllBooksSortedPagedResponse:");
        log.info(getAllBooksSortedPagedResponse.toString());
        log.info("Constants");
        log.info(GET_ALL_BOOKS_SORTED_BY_CREATED_TIME_PAGED_RESPONSE.toString());
        Assertions.assertThat(getAllBooksSortedPagedResponse).isEqualTo(GET_ALL_BOOKS_SORTED_BY_CREATED_TIME_PAGED_RESPONSE);
    }


}
