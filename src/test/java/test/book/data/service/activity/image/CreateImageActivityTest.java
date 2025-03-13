package test.book.data.service.activity.image;

import static book.data.service.constants.Constants.S3_IMAGE_BUCKET;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.activity.book.GetAllBooksPagedActivityTest.AUTH_TOKEN;
import static test.book.data.service.dao.BookDAOTest.BOOK_NAME;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_NUMBER_ONE;
import static test.book.data.service.dao.ImageDAOTest.IMAGE_NUMBER_ONE;
import static test.book.data.service.dao.ImageDAOTest.IMAGE_ONE;
import static test.book.data.service.manager.BookManagerTest.BYTES_ONE;
import static test.book.data.service.manager.BookManagerTest.CREATED_BY_ONE;
import static test.book.data.service.manager.BookManagerTest.FILE_TYPE_ONE;

import book.data.service.activity.image.CreateImageActivity;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.image.ImageManager;
import book.data.service.message.image.CreateImageRequest;
import book.data.service.message.image.CreateImageResponse;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CreateImageActivityTest {
    public static CreateImageRequest CREATE_IMAGE_REQUEST =
        CreateImageRequest.builder()
            .bookName(BOOK_NAME)
            .chapterNumber("" + CHAPTER_NUMBER_ONE)
            .imageNumber("" + IMAGE_NUMBER_ONE)
            .fileBytes(BYTES_ONE)
            .fileType(FILE_TYPE_ONE)
            .build();
    public static CreateImageResponse CREATE_IMAGE_RESPONSE =
        CreateImageResponse.builder()
            .image(IMAGE_ONE)
            .build();

    private CreateImageActivity createImageActivity;
    @Mock
    private ImageManager imageManager;
    @Mock
    private FirebaseService firebaseService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        createImageActivity = new CreateImageActivity(
            imageManager,
            firebaseService);
    }

    @Test
    public void testCreateImage() throws MalformedURLException, URISyntaxException {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(imageManager.createImage(
            BOOK_NAME,
            CHAPTER_NUMBER_ONE,
            IMAGE_NUMBER_ONE,
            S3_IMAGE_BUCKET,
            CREATED_BY_ONE,
            BYTES_ONE,
            FILE_TYPE_ONE
        )).thenReturn(IMAGE_ONE);
        CreateImageResponse createImageResponse =
            createImageActivity.createImage(CREATE_IMAGE_REQUEST, AUTH_TOKEN);
        Assertions.assertThat(createImageResponse).isEqualTo(CREATE_IMAGE_RESPONSE);
    }
}
