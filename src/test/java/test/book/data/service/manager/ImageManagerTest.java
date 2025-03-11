package test.book.data.service.manager;

import static org.mockito.Mockito.when;
import static test.book.data.service.dao.BookDAOTest.BOOK_NAME;
import static test.book.data.service.dao.BookDAOTest.CREATED_BY_ONE;
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

import book.data.service.dao.chapter.ChapterDAO;
import book.data.service.dao.image.ImageDAO;
import book.data.service.sqlmodel.image.Image;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ImageManagerTest {
    private ImageManager imageManager;

    @Mock
    private ImageDAO imageDAO;
    @Mock
    private ChapterDAO chapterDAO;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        imageManager = new ImageManager(
            imageDAO,
            chapterDAO
        );
    }

    @Test
    public void testGetImagesByBookNameAndChapterNumberPaged() {
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
    public void testCreateImage() {
        when(imageDAO.createImage(
            CHAPTER_ONE,
            IMAGE_NUMBER_ONE,
            S3_KEY_ONE,
            S3_BUCKET_ONE,
            RELATIVE_IMAGE_URL_ONE,
            CREATED_BY_ONE
        )).thenReturn(IMAGE_ONE);
        when(chapterDAO.getChapterByBookNameAndChapterNumber(
            BOOK_NAME,
            CHAPTER_NUMBER_ONE,
            CREATED_BY_ONE
        )).thenReturn(CHAPTER_ONE);
        Image image = imageManager.createImage(
            BOOK_NAME,
            CHAPTER_NUMBER_ONE,
            IMAGE_NUMBER_ONE,
            S3_KEY_ONE,
            S3_BUCKET_ONE,
            RELATIVE_IMAGE_URL_ONE,
            CREATED_BY_ONE
        );
        Assertions.assertThat(image).isEqualTo(IMAGE_ONE);
    }

}
