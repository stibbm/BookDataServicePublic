package test.book.data.service.activity.chapter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.activity.book.GetAllBooksPagedActivityTest.AUTH_TOKEN;
import static test.book.data.service.dao.BookDAOTest.BOOK_NAME;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_NUMBER_ONE;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_ONE;
import static test.book.data.service.manager.BookManagerTest.CREATED_BY_ONE;

import book.data.service.activity.chapter.GetChapterByBookNameAndChapterNumberActivity;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.chapter.ChapterManager;
import book.data.service.manager.chapter.GetChapterRequestLogManager;
import book.data.service.message.chapter.GetChapterByBookNameAndChapterNumberRequest;
import book.data.service.message.chapter.GetChapterByBookNameAndChapterNumberResponse;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GetChapterByBookNameAndChapterNumberActivityTest {
    public static final GetChapterByBookNameAndChapterNumberResponse GET_CHAPTER_BY_BOOK_NAME_AND_CHAPTER_NUMBER_RESPONSE =
        GetChapterByBookNameAndChapterNumberResponse.builder()
            .chapter(CHAPTER_ONE)
            .build();
    public static final GetChapterByBookNameAndChapterNumberRequest GET_CHAPTER_BY_BOOK_NAME_AND_CHAPTER_NUMBER_REQUEST =
        GetChapterByBookNameAndChapterNumberRequest.builder()
            .bookName(BOOK_NAME)
            .chapterNumber("" + CHAPTER_NUMBER_ONE)
            .build();

    @Mock
    private ChapterManager chapterManager;

    @Mock
    private FirebaseService firebaseService;

    @Mock
    private GetChapterRequestLogManager getChapterRequestLogManager;

    private GetChapterByBookNameAndChapterNumberActivity getChapterByBookNameAndChapterNumberActivity;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        getChapterByBookNameAndChapterNumberActivity = new GetChapterByBookNameAndChapterNumberActivity(
            chapterManager, firebaseService, getChapterRequestLogManager
        );
    }

    @Test
    public void testGetChapterByBookNameAndChapterNumber() {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(chapterManager.getChapterByBookNameAndChapterNumber(BOOK_NAME, CHAPTER_NUMBER_ONE, CREATED_BY_ONE))
            .thenReturn(CHAPTER_ONE);
        GetChapterByBookNameAndChapterNumberResponse getChapterByBookNameAndChapterNumberResponse =
            getChapterByBookNameAndChapterNumberActivity.getChapterByBookNameAndChapterNumber(
                GET_CHAPTER_BY_BOOK_NAME_AND_CHAPTER_NUMBER_REQUEST, AUTH_TOKEN
            );
        Assertions.assertThat(getChapterByBookNameAndChapterNumberResponse)
            .isEqualTo(GET_CHAPTER_BY_BOOK_NAME_AND_CHAPTER_NUMBER_RESPONSE);
    }
}
