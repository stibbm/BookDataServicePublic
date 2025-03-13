package test.book.data.service.activity.image;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.activity.book.GetAllBooksPagedActivityTest.AUTH_TOKEN;
import static test.book.data.service.dao.BookDAOTest.BOOK_NAME;
import static test.book.data.service.dao.BookDAOTest.PAGE_NUMBER;
import static test.book.data.service.dao.BookDAOTest.PAGE_SIZE;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_NUMBER_ONE;
import static test.book.data.service.dao.ImageDAOTest.IMAGE_LIST;

import book.data.service.activity.image.GetImagesByBookNameAndChapterNumberPagedActivity;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.RequestLogManager;
import book.data.service.manager.image.ImageManager;
import book.data.service.message.image.GetImagesByBookNameAndChapterNumberPagedRequest;
import book.data.service.message.image.GetImagesByBookNameAndChapterNumberPagedResponse;
import com.google.firebase.auth.FirebaseToken;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GetImagesByBookNameAndChapterNumberPagedActivityTest {
    public static final String CREATED_BY_ONE = "testCreatedByOne@gmail.com";
    public static final String CREATED_BY_TWO = "testCreatedByTwo@gmail.com";
    public static final GetImagesByBookNameAndChapterNumberPagedRequest GET_IMAGES_BY_BOOK_NAME_AND_CHAPTER_NUMBER_PAGED_REQUEST =
        GetImagesByBookNameAndChapterNumberPagedRequest.builder()
            .bookName(BOOK_NAME)
            .chapterNumber("" + CHAPTER_NUMBER_ONE)
            .pageNumber("" + PAGE_NUMBER)
            .pageSize("" + PAGE_SIZE)
            .build();
    public static final GetImagesByBookNameAndChapterNumberPagedResponse GET_IMAGES_BY_BOOK_NAME_AND_CHAPTER_NUMBER_PAGED_RESPONSE =
        GetImagesByBookNameAndChapterNumberPagedResponse.builder()
            .imageList(IMAGE_LIST)
            .build();

    private GetImagesByBookNameAndChapterNumberPagedActivity getImagesByBookNameAndChapterNumberPagedActivity;
    @Mock
    private ImageManager imageManager;
    @Mock
    private RequestLogManager requestLogManager;
    @Mock
    private FirebaseService firebaseService;
    @Mock
    private FirebaseToken firebaseToken;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        getImagesByBookNameAndChapterNumberPagedActivity = new GetImagesByBookNameAndChapterNumberPagedActivity(
            imageManager,
            requestLogManager,
            firebaseService
        );
    }

    @Test
    public void testGetImagesByBookNameAndChapterNumberPaged() {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(imageManager.getImagesByBookNameAndChapterNumberPaged(
            BOOK_NAME,
            CHAPTER_NUMBER_ONE,
            PAGE_NUMBER,
            PAGE_SIZE,
            CREATED_BY_ONE
        )).thenReturn(IMAGE_LIST);
        GetImagesByBookNameAndChapterNumberPagedResponse getImagesByBookNameAndChapterNumberPagedResponse =
            getImagesByBookNameAndChapterNumberPagedActivity.getImagesByBookNameAndChapterNumber(GET_IMAGES_BY_BOOK_NAME_AND_CHAPTER_NUMBER_PAGED_REQUEST, AUTH_TOKEN);
        Assertions.assertThat(getImagesByBookNameAndChapterNumberPagedResponse).isEqualTo(GET_IMAGES_BY_BOOK_NAME_AND_CHAPTER_NUMBER_PAGED_RESPONSE);
    }
}
