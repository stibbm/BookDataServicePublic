package test.book.data.service.activity.image;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.activity.book.GetAllBooksPagedActivityTest.AUTH_TOKEN;
import static test.book.data.service.dao.BookDAOTest.BOOK_NAME;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_NUMBER_ONE;
import static test.book.data.service.dao.ImageDAOTest.IMAGE_NUMBER_ONE;
import static test.book.data.service.dao.ImageDAOTest.IMAGE_ONE;
import static test.book.data.service.manager.BookManagerTest.CREATED_BY_ONE;

import book.data.service.activity.image.GetImageByBookNameAndChapterNameAndImageNumberActivity;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.RequestLogManager;
import book.data.service.manager.image.ImageManager;
import book.data.service.message.image.GetImageByBookNameAndChapterNumberAndImageNumberRequest;
import book.data.service.message.image.GetImageByBookNameAndChapterNumberAndImageNumberResponse;
import com.google.firebase.auth.FirebaseToken;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GetImageByBookNameAndChapterNameAndImageNumberActivityTest {
    private GetImageByBookNameAndChapterNumberAndImageNumberRequest
        GET_IMAGE_BY_BOOK_NAME_AND_CHAPTER_NUMBER_AND_IMAGE_NUMBER_REQUEST =
        GetImageByBookNameAndChapterNumberAndImageNumberRequest.builder()
            .bookName(BOOK_NAME)
            .chapterNumber("" + CHAPTER_NUMBER_ONE)
            .imageNumber("" + IMAGE_NUMBER_ONE)
            .build();
    private GetImageByBookNameAndChapterNumberAndImageNumberResponse
        GET_IMAGE_BY_BOOK_NAME_AND_CHAPTER_NUMBER_AND_IMAGE_NUMBER_RESPONSE =
        GetImageByBookNameAndChapterNumberAndImageNumberResponse.builder()
            .image(IMAGE_ONE)
            .build();

    private GetImageByBookNameAndChapterNameAndImageNumberActivity getImageByBookNameAndChapterNameAndImageNumberActivity;
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
        getImageByBookNameAndChapterNameAndImageNumberActivity = new GetImageByBookNameAndChapterNameAndImageNumberActivity(
            imageManager,
            requestLogManager,
            firebaseService
        );
    }

    @Test
    public void testGetImageByBookNameAndChapterNameAndImageNumber() {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(imageManager.getImageByBookNameAndChapterNameAndImageNumber(
            BOOK_NAME,
            CHAPTER_NUMBER_ONE,
            IMAGE_NUMBER_ONE,
            CREATED_BY_ONE
        )).thenReturn(IMAGE_ONE);
        GetImageByBookNameAndChapterNumberAndImageNumberResponse getImageByBookNameAndChapterNumberAndImageNumberResponse = getImageByBookNameAndChapterNameAndImageNumberActivity.getImageByBookNameAndChapterNumberAndImageNumber(
            GET_IMAGE_BY_BOOK_NAME_AND_CHAPTER_NUMBER_AND_IMAGE_NUMBER_REQUEST, AUTH_TOKEN
        );
        Assertions.assertThat(getImageByBookNameAndChapterNumberAndImageNumberResponse).isEqualTo(GET_IMAGE_BY_BOOK_NAME_AND_CHAPTER_NUMBER_AND_IMAGE_NUMBER_RESPONSE);
    }

}
