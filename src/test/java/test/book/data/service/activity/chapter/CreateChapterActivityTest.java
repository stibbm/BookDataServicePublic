package test.book.data.service.activity.chapter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.activity.book.GetAllBooksPagedActivityTest.AUTH_TOKEN;
import static test.book.data.service.dao.BookDAOTest.BOOK_NAME;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_NAME_ONE;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_NUMBER_ONE;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_ONE;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_VIEWS_ONE;
import static test.book.data.service.manager.BookManagerTest.CREATED_BY_ONE;
import book.data.service.activity.chapter.CreateChapterActivity;
import book.data.service.exception.chapter.ChapterAlreadyExistsException;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.chapter.ChapterManager;
import book.data.service.message.chapter.CreateChapterRequest;
import book.data.service.message.chapter.CreateChapterResponse;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CreateChapterActivityTest {
    public static final CreateChapterRequest CREATE_CHAPTER_REQUEST =
        CreateChapterRequest.builder()
            .bookName(BOOK_NAME)
            .chapterNumber("" + CHAPTER_NUMBER_ONE)
            .chapterName(CHAPTER_NAME_ONE)
            .chapterViews("" + CHAPTER_VIEWS_ONE)
            .build();
    public static final CreateChapterResponse CREATE_CHAPTER_RESPONSE =
        CreateChapterResponse.builder()
            .chapter(CHAPTER_ONE)
            .build();

    private CreateChapterActivity createChapterActivity;
    @Mock
    private ChapterManager chapterManager;
    @Mock
    private FirebaseService firebaseService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        createChapterActivity =
            new CreateChapterActivity(chapterManager, firebaseService);
    }

    @Test
    public void testCreateChapter() {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(this.chapterManager.createChapter(
            BOOK_NAME,
            CHAPTER_NUMBER_ONE,
            CHAPTER_NAME_ONE,
            CHAPTER_VIEWS_ONE,
            CREATED_BY_ONE
        )).thenReturn(CHAPTER_ONE);
        CreateChapterResponse createChapterResponse =
            createChapterActivity.createChapter(CREATE_CHAPTER_REQUEST, CREATED_BY_ONE);
        Assertions.assertThat(createChapterResponse).isEqualTo(CREATE_CHAPTER_RESPONSE);
    }

    @Test
    public void testCreateChapter_ChapterAlreadyExistsException() {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(this.chapterManager.createChapter(
            BOOK_NAME,
            CHAPTER_NUMBER_ONE,
            CHAPTER_NAME_ONE,
            CHAPTER_VIEWS_ONE,
            CREATED_BY_ONE
        )).thenThrow(new ChapterAlreadyExistsException());
        Assert.assertThrows(ChapterAlreadyExistsException.class, () -> {
            CreateChapterResponse createChapterResponse =
                createChapterActivity.createChapter(CREATE_CHAPTER_REQUEST, AUTH_TOKEN);
        });
    }

}
