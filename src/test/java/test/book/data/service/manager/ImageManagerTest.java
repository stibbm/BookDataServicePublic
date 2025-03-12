package test.book.data.service.manager;

import book.data.service.clientwrapper.S3ClientWrapper;
import book.data.service.dao.chapter.ChapterDAO;
import book.data.service.dao.image.ImageDAO;
import book.data.service.manager.image.ImageManager;
import book.data.service.service.id.IdService;
import book.data.service.service.time.TimeService;
import book.data.service.sqlmodel.chapter.Chapter;
import book.data.service.sqlmodel.image.Image;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.dao.BookDAOTest.BOOK_NAME;
import static test.book.data.service.dao.BookDAOTest.PAGE_NUMBER;
import static test.book.data.service.dao.BookDAOTest.PAGE_SIZE;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_NUMBER_ONE;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_ONE;
import static test.book.data.service.dao.ImageDAOTest.IMAGE_LIST;
import static test.book.data.service.dao.ImageDAOTest.IMAGE_NUMBER_ONE;
import static test.book.data.service.dao.ImageDAOTest.IMAGE_ONE;
import static test.book.data.service.dao.ImageDAOTest.RELATIVE_IMAGE_URL_ONE;
import static test.book.data.service.dao.ImageDAOTest.S3_BUCKET_ONE;
import static test.book.data.service.dao.ImageDAOTest.S3_KEY_ONE;
import static test.book.data.service.manager.BookManagerTest.CREATED_AT_EPOCH_MILLI_TIME_ONE;
import static test.book.data.service.manager.BookManagerTest.CREATED_BY_ONE;


@Slf4j
public class ImageManagerTest {

    public static final String S3_KEY_ONE = "s3KeyOne";
    public static byte[] BYTES_ONE = "BytesOne".getBytes();
    public static final String FILE_TYPE_ONE = ".jpg";
    public static final String RELATIVE_IMAGE_URL_ONE = "ogeihwiogewo";
    private ImageManager imageManager;

    @Mock
    private ImageDAO imageDAO;
    @Mock
    private ChapterDAO chapterDAO;
    @Mock
    private S3ClientWrapper s3ClientWrapper;
    @Mock
    private IdService idService;
    @Mock
    private TimeService timeService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        imageManager = new ImageManager(
                imageDAO,
                chapterDAO,
                s3ClientWrapper,
                idService,
                timeService
        );
    }

    @Test
    public void testGetImagesByBookNameAndChapterNumberPaged()
            throws MalformedURLException, URISyntaxException {
        when(idService.generateS3Key(anyLong(), anyLong(), anyLong(), anyString()))
                .thenReturn(S3_KEY_ONE);
        when(idService.generateRelativeImageUrl(
                anyLong(), anyLong(), anyLong(), anyString()
        )).thenReturn(RELATIVE_IMAGE_URL_ONE);
        when(imageDAO.getImagesByBookNameAndChapterNumberPaged(
                BOOK_NAME,
                CHAPTER_NUMBER_ONE,
                PAGE_NUMBER,
                PAGE_SIZE,
                CREATED_BY_ONE
        )).thenReturn(IMAGE_LIST);
        List<Image> imageList = imageManager.getImagesByBookNameAndChapterNumberPaged(
                BOOK_NAME,
                CHAPTER_NUMBER_ONE,
                PAGE_NUMBER,
                PAGE_SIZE,
                CREATED_BY_ONE
        );
        Assertions.assertThat(imageList).isEqualTo(IMAGE_LIST);
    }

    @Test
    public void testGetImageByBookNameAndChapterNumberAndImageNumber() {
        when(imageDAO.getImageByBookNameAndChapterNumberAndImageNumber(
                BOOK_NAME,
                CHAPTER_NUMBER_ONE,
                IMAGE_NUMBER_ONE,
                CREATED_BY_ONE
        )).thenReturn(IMAGE_ONE);
        Image image = imageManager.getImageByBookNameAndChapterNameAndImageNumber(
                BOOK_NAME,
                CHAPTER_NUMBER_ONE,
                IMAGE_NUMBER_ONE,
                CREATED_BY_ONE
        );
        Assertions.assertThat(image).isEqualTo(IMAGE_ONE);
    }

    @Test
    public void testCreateImage() throws MalformedURLException, URISyntaxException {
        when(imageDAO.createImage(
                any(Chapter.class),
                anyLong(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyLong()
        )).thenReturn(IMAGE_ONE);
        when(idService.generateS3Key(
                anyLong(),
                anyLong(),
                anyLong(),
                anyString()
        )).thenReturn(S3_KEY_ONE);
        when(idService.generateRelativeImageUrl(
                anyLong(),
                anyLong(),
                anyLong(),
                anyString()
        )).thenReturn(RELATIVE_IMAGE_URL_ONE);
        when(chapterDAO.getChapterByBookNameAndChapterNumber(
                anyString(),
                anyLong(),
                anyString()
        )).thenReturn(CHAPTER_ONE);
        when(timeService.getCurrentLocalDateTimeLong()).thenReturn(CREATED_AT_EPOCH_MILLI_TIME_ONE);
        Image image = imageManager.createImage(
                BOOK_NAME,
                CHAPTER_NUMBER_ONE,
                IMAGE_NUMBER_ONE,
                S3_BUCKET_ONE,
                CREATED_BY_ONE,
                BYTES_ONE,
                FILE_TYPE_ONE
        );
        log.info("image: " + image);
        Assertions.assertThat(image).isEqualTo(IMAGE_ONE);
    }

}
