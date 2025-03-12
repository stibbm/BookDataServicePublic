package test.book.data.service.dao;


import book.data.service.dao.image.ImageDAO;
import book.data.service.exception.book.BookDoesNotExistException;
import book.data.service.exception.chapter.ChapterDoesNotExistException;
import book.data.service.exception.image.ImageAlreadyExistsException;
import book.data.service.repository.BookRepository;
import book.data.service.repository.ChapterRepository;
import book.data.service.repository.ImageRepository;
import book.data.service.sqlmodel.image.Image;
import book.data.service.sqlmodel.image.ImageId;
import com.google.common.collect.ImmutableList;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.dao.BookDAOTest.*;
import static test.book.data.service.dao.ChapterDAOTest.*;
import static test.book.data.service.manager.BookManagerTest.*;

public class ImageDAOTest {

    public static final Long IMAGE_NUMBER_ONE = 1L;
    public static final Long IMAGE_NUMBER_TWO = 2L;
    public static final String S3_KEY_ONE = "S3KeyOne";
    public static final String S3_KEY_TWO = "S3KeyTwo";
    public static final String S3_BUCKET_ONE = "S3BucketOne";
    public static final String S3_BUCKET_TWO = "S3BucketTwo";
    public static final String RELATIVE_IMAGE_URL_ONE = "?urlthing";
    public static final String RELATIVE_IMAGE_URL_TWO = "?oeghoiwe";
    public static ImageId IMAGE_ID_ONE = new ImageId(
            IMAGE_NUMBER_ONE, CHAPTER_ONE
    );
    public static ImageId IMAGE_ID_TWO = new ImageId(
            IMAGE_NUMBER_TWO, CHAPTER_TWO
    );
    public static Image IMAGE_ONE = new Image(
            IMAGE_ID_ONE,
            S3_KEY_ONE,
            S3_BUCKET_ONE,
            RELATIVE_IMAGE_URL_ONE,
            CREATED_BY_ONE,
            FILE_TYPE_ONE,
            CREATED_AT_EPOCH_MILLI_TIME_ONE
    );
    public static Image IMAGE_TWO = new Image(
            IMAGE_ID_TWO,
            S3_KEY_TWO,
            S3_BUCKET_TWO,
            RELATIVE_IMAGE_URL_TWO,
            CREATED_BY_TWO,
            FILE_TYPE_ONE,
            CREATED_AT_EPOCH_MILLI_TIME_TWO
    );

    public static List<Image> IMAGE_LIST = ImmutableList.of(IMAGE_ONE, IMAGE_TWO);

    private ImageDAO imageDAO;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ChapterRepository chapterRepository;
    @Mock
    private BookRepository bookRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        imageDAO = new ImageDAO(imageRepository, chapterRepository, bookRepository);
    }

    @Test
    public void testGetImagesByBookNameAndChapterNumberPaged() {
        PageRequest pageRequest = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        when(imageRepository.findImagesByBookNameAndChapterNumberPaged(
                BOOK_NAME,
                CHAPTER_NUMBER_ONE,
                pageRequest,
                CREATED_BY_ONE
        )).thenReturn(IMAGE_LIST);
        when(bookRepository.doesBookExist(BOOK_NAME, CREATED_BY_ONE)).thenReturn(true);
        when(chapterRepository.findChapterByOnlyBookNumberAndChapterNumber(anyLong(), anyLong()))
                .thenReturn(CHAPTER_ONE);
        when(chapterRepository.findChapterByOnlyBookNumberAndChapterNumber(anyLong(), anyLong()))
                .thenReturn(CHAPTER_ONE);
        when(chapterRepository.findChapterByBookNameAndChapterNumber(anyString(), anyLong(), anyString()))
                .thenReturn(CHAPTER_ONE);
        when(chapterRepository.doesChapterExist(BOOK_NAME, CHAPTER_NUMBER_ONE,
                CREATED_BY_ONE)).thenReturn(true);
        List<Image> imageList = imageDAO.getImagesByBookNameAndChapterNumberPaged(
                BOOK_NAME,
                CHAPTER_NUMBER_ONE,
                PAGE_NUMBER,
                PAGE_SIZE,
                CREATED_BY_ONE
        );
        Assertions.assertThat(imageList).isEqualTo(IMAGE_LIST);
    }

    @Test
    public void testGetImagesByBookNameAndChapterNumberPaged_BookDoesNotExistException() {
        when(bookRepository.doesBookExist(BOOK_NAME, CREATED_BY_ONE)).thenReturn(false);
        Assert.assertThrows(BookDoesNotExistException.class, () -> {
            imageDAO.getImagesByBookNameAndChapterNumberPaged(
                    BOOK_NAME,
                    CHAPTER_NUMBER_ONE,
                    PAGE_NUMBER,
                    PAGE_SIZE,
                    CREATED_BY_ONE
            );
        });
    }

    @Test
    public void testGetImagesByBookNameAndChapterNumberPaged_ChapterDoesNotExistException() {
        when(bookRepository.doesBookExist(BOOK_NAME, CREATED_BY_ONE)).thenReturn(true);
        when(chapterRepository.doesChapterExist(BOOK_NAME, CHAPTER_NUMBER_ONE, CREATED_BY_ONE))
                .thenReturn(false);
        Assert.assertThrows(ChapterDoesNotExistException.class, () -> {
            imageDAO.getImagesByBookNameAndChapterNumberPaged(
                    BOOK_NAME,
                    CHAPTER_NUMBER_ONE,
                    PAGE_NUMBER,
                    PAGE_SIZE,
                    CREATED_BY_ONE
            );
        });
    }

    @Test
    public void testGetImageByBookNameAndChapterNumberAndImageNumber() {
        when(bookRepository.doesBookExist(BOOK_NAME, CREATED_BY_ONE)).thenReturn(true);
        when(chapterRepository.doesChapterExist(BOOK_NAME, CHAPTER_NUMBER_ONE,
                CREATED_BY_ONE)).thenReturn(true);
        when(imageRepository.doesImageExist(BOOK_NAME, CHAPTER_NUMBER_ONE, IMAGE_NUMBER_ONE,
                CREATED_BY_ONE)).thenReturn(true);
        when(imageRepository.findImageByBookNameAndChapterNumberAndImageNumber(
                BOOK_NAME,
                CHAPTER_NUMBER_ONE,
                IMAGE_NUMBER_ONE,
                CREATED_BY_ONE
        )).thenReturn(IMAGE_ONE);
        Image image = imageDAO.getImageByBookNameAndChapterNumberAndImageNumber(
                BOOK_NAME,
                CHAPTER_NUMBER_ONE,
                IMAGE_NUMBER_ONE,
                CREATED_BY_ONE
        );
        Assertions.assertThat(image).isEqualTo(IMAGE_ONE);
    }

    @Test
    public void testCreateImage() {
        when(bookRepository.doesBookExist(BOOK_NAME, CREATED_BY_ONE)).thenReturn(true);
        when(chapterRepository.doesChapterExist(BOOK_NAME, CHAPTER_NUMBER_ONE,
                CREATED_BY_ONE)).thenReturn(true);
        when(imageRepository.doesImageExist(BOOK_NAME, CHAPTER_NUMBER_ONE, IMAGE_NUMBER_ONE,
                CREATED_BY_ONE))
                .thenReturn(false);
        Image image = imageDAO.createImage(
                CHAPTER_ONE,
                IMAGE_NUMBER_ONE,
                S3_KEY_ONE,
                S3_BUCKET_ONE,
                RELATIVE_IMAGE_URL_ONE,
                CREATED_BY_ONE,
                FILE_TYPE_ONE,
                CREATED_AT_EPOCH_MILLI_TIME_ONE
        );
        Assertions.assertThat(image).isEqualTo(IMAGE_ONE);
    }

    @Test
    public void testCreateImage_BookDoesNotExistException() {
        when(bookRepository.doesBookExist(BOOK_NAME, CREATED_BY_ONE)).thenReturn(false);
        Assert.assertThrows(BookDoesNotExistException.class, () -> {
            imageDAO.createImage(
                    CHAPTER_ONE,
                    IMAGE_NUMBER_ONE,
                    S3_KEY_ONE,
                    S3_BUCKET_ONE,
                    RELATIVE_IMAGE_URL_ONE,
                    CREATED_BY_ONE,
                    FILE_TYPE_ONE,
                    CREATED_AT_EPOCH_MILLI_TIME_ONE
            );
        });
    }

    @Test
    public void testCreateImage_ChapterDoesNotExistException() {
        when(bookRepository.doesBookExist(BOOK_NAME, CREATED_BY_ONE)).thenReturn(true);
        when(chapterRepository.doesChapterExist(BOOK_NAME, CHAPTER_NUMBER_ONE,
                CREATED_BY_ONE)).thenReturn(false);
        Assert.assertThrows(ChapterDoesNotExistException.class, () -> {
            imageDAO.createImage(
                    CHAPTER_ONE,
                    IMAGE_NUMBER_ONE,
                    S3_KEY_ONE,
                    S3_BUCKET_ONE,
                    RELATIVE_IMAGE_URL_ONE,
                    CREATED_BY_ONE,
                    FILE_TYPE_ONE,
                    CREATED_AT_EPOCH_MILLI_TIME_ONE
            );
        });
    }

    @Test
    public void testCreateImage_ImageAlreadyExistsException() {
        when(bookRepository.doesBookExist(BOOK_NAME, CREATED_BY_ONE)).thenReturn(true);
        when(chapterRepository.doesChapterExist(BOOK_NAME, CHAPTER_NUMBER_ONE,
                CREATED_BY_ONE)).thenReturn(true);
        when(imageRepository.doesImageExist(BOOK_NAME, CHAPTER_NUMBER_ONE, IMAGE_NUMBER_ONE,
                CREATED_BY_ONE))
                .thenReturn(true);
        Assert.assertThrows(ImageAlreadyExistsException.class, () -> {
            imageDAO.createImage(
                    CHAPTER_ONE,
                    IMAGE_NUMBER_ONE,
                    S3_KEY_ONE,
                    S3_BUCKET_ONE,
                    RELATIVE_IMAGE_URL_ONE,
                    CREATED_BY_ONE,
                    FILE_TYPE_ONE,
                    CREATED_AT_EPOCH_MILLI_TIME_ONE
            );
        });
    }
}
