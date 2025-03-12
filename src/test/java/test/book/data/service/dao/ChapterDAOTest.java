package test.book.data.service.dao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.manager.BookManagerTest.CREATED_AT_EPOCH_MILLI_TIME_ONE;
import static test.book.data.service.manager.BookManagerTest.CREATED_AT_EPOCH_MILLI_TIME_TWO;
import static test.book.data.service.manager.BookManagerTest.CREATED_BY_ONE;

import book.data.service.dao.chapter.ChapterDAO;
import book.data.service.exception.book.BookDoesNotExistException;
import book.data.service.exception.chapter.ChapterDoesNotExistException;
import book.data.service.firebase.FirebaseService;
import book.data.service.repository.BookRepository;
import book.data.service.repository.ChapterRepository;
import book.data.service.sqlmodel.chapter.Chapter;
import book.data.service.sqlmodel.chapter.ChapterId;
import book.data.service.sqlmodel.chapter.LockStatus;
import com.google.common.collect.ImmutableList;
import com.google.firebase.auth.FirebaseToken;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import test.book.data.service.manager.BookManagerTest;

public class ChapterDAOTest {

    public static final LockStatus LOCK_STATUS_ONE_EDITABLE = LockStatus.EDITABLE;
    public static final LockStatus LOCK_STATUS_TWO_LOCKED = LockStatus.LOCKED;
    public static final String CHAPTER_NAME_ONE = "chapterNameOne";
    public static final Long CHAPTER_VIEWS_ONE = 100L;
    public static final Long CHAPTER_NUMBER_ONE = 1L;
    public static final String CHAPTER_NAME_TWO = "chapterNameTwo";
    public static final Long CHAPTER_VIEWS_TWO = 100L;
    public static final Long CHAPTER_NUMBER_TWO = 2L;
    public static final ChapterId CHAPTER_ID_ONE =
            new ChapterId(CHAPTER_NUMBER_ONE, BookDAOTest.BOOK_ONE);
    public static final ChapterId CHAPTER_ID_TWO =
            new ChapterId(CHAPTER_NUMBER_TWO, BookDAOTest.BOOK_ONE);

    public static final Chapter CHAPTER_ONE =
            new Chapter(
                    CHAPTER_ID_ONE,
                    CHAPTER_NAME_ONE,
                    CHAPTER_VIEWS_ONE,
                    CREATED_BY_ONE,
                    CREATED_AT_EPOCH_MILLI_TIME_ONE,
                    LOCK_STATUS_ONE_EDITABLE
            );
    public static final Chapter CHAPTER_TWO =
            new Chapter(
                    CHAPTER_ID_TWO,
                    CHAPTER_NAME_TWO,
                    CHAPTER_VIEWS_TWO,
                    BookManagerTest.CREATED_BY_TWO,
                    CREATED_AT_EPOCH_MILLI_TIME_TWO,
                    LOCK_STATUS_ONE_EDITABLE
            );
    public static final List<Chapter> CHAPTER_LIST =
            ImmutableList.of(CHAPTER_ONE, CHAPTER_TWO);

    private ChapterDAO chapterDAO;

    @Mock
    private ChapterRepository chapterRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private FirebaseService firebaseService;
    @Mock
    private FirebaseToken firebaseToken;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        chapterDAO = new ChapterDAO(chapterRepository, bookRepository);
        when(firebaseService.verifyToken(anyString())).thenReturn(firebaseToken);
        when(firebaseToken.getEmail()).thenReturn(CREATED_BY_ONE);
    }

    @Test
    public void testGetAllChaptersPaged() {
        when(chapterRepository.findAllChaptersPaged(any(PageRequest.class)))
                .thenReturn(CHAPTER_LIST);
        List<Chapter> chapterList = chapterDAO.getAllChaptersPaged(BookDAOTest.PAGE_SIZE,
                BookDAOTest.PAGE_NUMBER);
        Assertions.assertThat(chapterList).isEqualTo(CHAPTER_LIST);
    }

    @Test
    public void testGetChaptersByBookNamePaged() {
        when(bookRepository.doesBookExist(anyString(), anyString())).thenReturn(true);
        when(chapterRepository.findChaptersByBookNamePaged(anyString(), any(PageRequest.class)))
                .thenReturn(CHAPTER_LIST);
        List<Chapter> chaptersList = chapterDAO.getChaptersByBookNamePaged(BookDAOTest.BOOK_NAME,
                BookDAOTest.PAGE_NUMBER, BookDAOTest.PAGE_SIZE, CREATED_BY_ONE);
        Assertions.assertThat(chaptersList).isEqualTo(CHAPTER_LIST);
    }

    @Test
    public void testGetChaptersByBookNamePaged_BookDoesNotExistException() {
        when(bookRepository.doesBookExist(BookDAOTest.BOOK_NAME, CREATED_BY_ONE)).thenReturn(false);
        Assert.assertThrows(BookDoesNotExistException.class, () -> {
            chapterDAO.getChaptersByBookNamePaged(
                    BookDAOTest.BOOK_NAME,
                    BookDAOTest.PAGE_NUMBER,
                    BookDAOTest.PAGE_SIZE,
                    CREATED_BY_ONE
            );
        });
    }

    @Test
    public void testGetChapterByBookNameAndChapterNumber() {
        when(bookRepository.doesBookExist(BookDAOTest.BOOK_NAME, CREATED_BY_ONE)).thenReturn(true);
        when(chapterRepository.doesChapterExist(BookDAOTest.BOOK_NAME, CHAPTER_NUMBER_ONE,
                CREATED_BY_ONE)).thenReturn(true);
        when(chapterRepository.findChapterByBookNameAndChapterNumber(
                anyString(),
                anyLong(),
                anyString()
        )).thenReturn(CHAPTER_ONE);
        Chapter chapter = chapterDAO.getChapterByBookNameAndChapterNumber(
                BookDAOTest.BOOK_NAME,
                CHAPTER_NUMBER_ONE,
                CREATED_BY_ONE
        );
        Assertions.assertThat(CHAPTER_ONE).isEqualTo(chapter);
    }

    @Test
    public void testGetChapterByBookNameAndChapterName_BookDoesNotExistException() {
        when(bookRepository.doesBookExist(BookDAOTest.BOOK_NAME, CREATED_BY_ONE)).thenReturn(false);
        Assert.assertThrows(BookDoesNotExistException.class, () -> {
            chapterDAO.getChapterByBookNameAndChapterNumber(
                    BookDAOTest.BOOK_NAME,
                    CHAPTER_NUMBER_ONE,
                    CREATED_BY_ONE
            );
        });
    }

    @Test
    public void testGetChapterByBookNameAndChapterName_ChapterDoesNotExistException() {
        when(bookRepository.doesBookExist(BookDAOTest.BOOK_NAME, CREATED_BY_ONE)).thenReturn(true);
        when(chapterRepository.doesChapterExist(BookDAOTest.BOOK_NAME, CHAPTER_NUMBER_ONE,
                CREATED_BY_ONE)).thenReturn(false);
        Assert.assertThrows(ChapterDoesNotExistException.class, () -> {
            chapterDAO.getChapterByBookNameAndChapterNumber(
                    BookDAOTest.BOOK_NAME,
                    CHAPTER_NUMBER_ONE,
                    CREATED_BY_ONE
            );
        });
    }

    @Test
    public void testCreateChapter() {
        when(bookRepository.doesBookExist(BookDAOTest.BOOK_NAME, CREATED_BY_ONE)).thenReturn(true);
        when(chapterRepository.doesChapterExist(BookDAOTest.BOOK_NAME, CHAPTER_NUMBER_ONE,
                CREATED_BY_ONE)).thenReturn(false);
        Chapter chapter = chapterDAO.createChapter(
                BookDAOTest.BOOK_ONE,
                CHAPTER_NUMBER_ONE,
                CHAPTER_NAME_ONE,
                CHAPTER_VIEWS_ONE,
                CREATED_BY_ONE,
                CREATED_AT_EPOCH_MILLI_TIME_ONE,
                LOCK_STATUS_ONE_EDITABLE
        );
        Assertions.assertThat(chapter).isEqualTo(CHAPTER_ONE);
    }

}
