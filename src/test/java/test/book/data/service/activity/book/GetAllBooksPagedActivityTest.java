package test.book.data.service.activity.book;

import book.data.service.activity.book.GetAllBooksPagedActivity;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.book.BookManager;
import book.data.service.message.book.GetAllBooksPagedRequest;
import book.data.service.message.book.GetAllBooksPagedResponse;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.dao.BookDAOTest.BOOK_LIST;
import static test.book.data.service.dao.BookDAOTest.PAGE_NUMBER;
import static test.book.data.service.dao.BookDAOTest.PAGE_SIZE;
import static test.book.data.service.manager.BookManagerTest.CREATED_BY_ONE;

public class GetAllBooksPagedActivityTest {
    public static final String AUTH_TOKEN = "ogiheohes";
    public static final GetAllBooksPagedRequest GET_ALL_BOOKS_PAGED_REQUEST =
            GetAllBooksPagedRequest.builder().pageNumber("" + PAGE_NUMBER).pageSize("" + PAGE_SIZE)
                    .build();
    public static final GetAllBooksPagedResponse GET_ALL_BOOKS_PAGED_RESPONSE =
            GetAllBooksPagedResponse.builder()
                    .bookList(BOOK_LIST)
                    .pageNumber(PAGE_NUMBER)
                    .pageSize(PAGE_SIZE)
                    .build();

    private GetAllBooksPagedActivity getAllBooksPagedActivity;

    @Mock
    private BookManager bookManager;
    @Mock
    private FirebaseService firebaseService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.getAllBooksPagedActivity = new GetAllBooksPagedActivity(bookManager, firebaseService);
    }

    @Test
    public void testGetAllBooksPaged() {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(bookManager.getAllBooksPaged(PAGE_NUMBER, PAGE_SIZE, CREATED_BY_ONE))
                .thenReturn(BOOK_LIST);
        GetAllBooksPagedResponse getAllBooksPagedResponse =
                getAllBooksPagedActivity.getAllBooksPaged(GET_ALL_BOOKS_PAGED_REQUEST, AUTH_TOKEN);
        Assertions.assertThat(getAllBooksPagedResponse).isEqualTo(GET_ALL_BOOKS_PAGED_RESPONSE);
    }

}
