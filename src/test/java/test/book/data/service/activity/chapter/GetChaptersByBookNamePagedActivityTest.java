package test.book.data.service.activity.chapter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.activity.book.GetAllBooksPagedActivityTest.AUTH_TOKEN;
import static test.book.data.service.dao.BookDAOTest.BOOK_NAME;
import static test.book.data.service.dao.BookDAOTest.PAGE_NUMBER;
import static test.book.data.service.dao.BookDAOTest.PAGE_SIZE;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_LIST;
import static test.book.data.service.dao.RequestLogDAOTest.REQUEST_LOG_ONE;
import static test.book.data.service.manager.BookManagerTest.CREATED_BY_ONE;

import book.data.service.activity.chapter.GetChaptersByBookNamePagedActivity;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.RequestLogManager;
import book.data.service.manager.ResponseLogManager;
import book.data.service.manager.chapter.ChapterManager;
import book.data.service.message.chapter.GetChaptersByBookNamePagedRequest;
import book.data.service.message.chapter.GetChaptersByBookNamePagedResponse;
import com.google.firebase.auth.FirebaseToken;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import test.book.data.service.dao.BookDAOTest;

public class GetChaptersByBookNamePagedActivityTest {
    public static final GetChaptersByBookNamePagedRequest GET_CHAPTERS_BY_BOOK_NAME_PAGED_REQUEST =
        GetChaptersByBookNamePagedRequest.builder()
            .bookName(BOOK_NAME)
            .pageNumber("" + PAGE_NUMBER)
            .pageSize("" + PAGE_SIZE)
            .build();
    public static final GetChaptersByBookNamePagedResponse GET_CHAPTERS_BY_BOOK_NAME_PAGED_RESPONSE =
        GetChaptersByBookNamePagedResponse.builder()
            .chapterList(CHAPTER_LIST)
            .pageNumber(PAGE_NUMBER)
            .pageSize(PAGE_SIZE)
            .build();

    private GetChaptersByBookNamePagedActivity getChaptersByBookNamePagedActivity;

    @Mock
    private ChapterManager chapterManager;
    @Mock
    private RequestLogManager requestLogManager;
    @Mock
    private ResponseLogManager responseLogManager;
    @Mock
    private FirebaseService firebaseService;
    @Mock
    private FirebaseToken firebaseToken;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        getChaptersByBookNamePagedActivity = new GetChaptersByBookNamePagedActivity(
            chapterManager,
            requestLogManager,
            responseLogManager,
            firebaseService
        );
    }

    @Test
    public void testGetChaptersByBookNamePaged() {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(requestLogManager.createRequestLog(
            anyString(),
            anyString()
        )).thenReturn(REQUEST_LOG_ONE);
        when(chapterManager.getChaptersByBookNamePaged(
            BOOK_NAME,
            PAGE_NUMBER,
            PAGE_SIZE,
            CREATED_BY_ONE
        )).thenReturn(CHAPTER_LIST);
        GetChaptersByBookNamePagedResponse getChaptersByBookNamePagedResponse = getChaptersByBookNamePagedActivity.getChaptersByBookNamePaged(
            GET_CHAPTERS_BY_BOOK_NAME_PAGED_REQUEST, AUTH_TOKEN
        );
        Assertions.assertThat(getChaptersByBookNamePagedResponse).isEqualTo(GET_CHAPTERS_BY_BOOK_NAME_PAGED_RESPONSE);
    }

    @Test
    public void testGetChaptersByBookNamePaged_BookDoesNotExistException() {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(requestLogManager.createRequestLog(
            anyString(),
            anyString()
        )).thenReturn(REQUEST_LOG_ONE);
        when(chapterManager.getChaptersByBookNamePaged(BOOK_NAME, PAGE_NUMBER, PAGE_SIZE, CREATED_BY_ONE))
            .thenReturn(CHAPTER_LIST);
        GetChaptersByBookNamePagedResponse getChaptersByBookNamePagedResponse =
            getChaptersByBookNamePagedActivity.getChaptersByBookNamePaged(
                GET_CHAPTERS_BY_BOOK_NAME_PAGED_REQUEST, AUTH_TOKEN
            );
        Assertions.assertThat(getChaptersByBookNamePagedResponse)
            .isEqualTo(GET_CHAPTERS_BY_BOOK_NAME_PAGED_RESPONSE);
    }

}
