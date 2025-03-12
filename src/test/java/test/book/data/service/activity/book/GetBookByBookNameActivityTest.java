package test.book.data.service.activity.book;


import book.data.service.activity.book.GetBookByBookNameActivity;
import book.data.service.exception.book.BookDoesNotExistException;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.book.BookManager;
import book.data.service.manager.book.GetBookRequestLogManager;
import book.data.service.message.book.GetBookByBookNameRequest;
import book.data.service.message.book.GetBookByBookNameResponse;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.activity.book.GetAllBooksPagedActivityTest.AUTH_TOKEN;
import static test.book.data.service.dao.BookDAOTest.BOOK_NAME;
import static test.book.data.service.dao.BookDAOTest.BOOK_ONE;
import static test.book.data.service.manager.BookManagerTest.CREATED_BY_ONE;

public class GetBookByBookNameActivityTest {
    public static final GetBookByBookNameRequest GET_BOOK_BY_BOOK_NAME_REQUEST =
            GetBookByBookNameRequest.builder().bookName(BOOK_NAME).build();
    public static final GetBookByBookNameResponse GET_BOOK_BY_BOOK_NAME_RESPONSE =
            GetBookByBookNameResponse.builder().book(BOOK_ONE).build();

    private GetBookByBookNameActivity getBookByBookNameActivity;
    @Mock
    private BookManager bookManager;
    @Mock
    private GetBookRequestLogManager getBookRequestLogManager;
    @Mock
    private FirebaseService firebaseService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        getBookByBookNameActivity = new GetBookByBookNameActivity(
                bookManager,
                firebaseService,
                getBookRequestLogManager
        );
    }

    @Test
    public void testGetBookByBookName() {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(this.bookManager.getBookByBookName(BOOK_NAME, CREATED_BY_ONE)).thenReturn(BOOK_ONE);
        GetBookByBookNameResponse getBookByBookNameResponse = getBookByBookNameActivity
                .getBookByBookName(GET_BOOK_BY_BOOK_NAME_REQUEST, CREATED_BY_ONE);
        Assertions.assertThat(getBookByBookNameResponse).isEqualTo(GET_BOOK_BY_BOOK_NAME_RESPONSE);
    }

    @Test
    public void testGetBookByBookName_BookDoesNotExistException() {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(this.bookManager.getBookByBookName(BOOK_NAME, CREATED_BY_ONE))
                .thenThrow(new BookDoesNotExistException());
        Assert.assertThrows(BookDoesNotExistException.class, () -> {
            getBookByBookNameActivity.getBookByBookName(GET_BOOK_BY_BOOK_NAME_REQUEST, AUTH_TOKEN);
        });
    }

}
