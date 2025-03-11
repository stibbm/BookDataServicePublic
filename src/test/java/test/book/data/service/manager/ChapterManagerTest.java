package test.book.data.service.manager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;
import static test.book.data.service.dao.BookDAOTest.BOOK_NAME;
import static test.book.data.service.dao.BookDAOTest.BOOK_ONE;
import static test.book.data.service.dao.BookDAOTest.CREATED_BY_ONE;
import static test.book.data.service.dao.BookDAOTest.CREATED_BY_TWO;
import static test.book.data.service.dao.BookDAOTest.PAGE_NUMBER;
import static test.book.data.service.dao.BookDAOTest.PAGE_SIZE;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_LIST;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_NAME_ONE;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_NUMBER_ONE;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_ONE;
import static test.book.data.service.dao.ChapterDAOTest.CHAPTER_VIEWS_ONE;

import book.data.service.dao.book.BookDAO;
import book.data.service.dao.chapter.ChapterDAO;
import book.data.service.exception.book.BookDoesNotExistException;
import book.data.service.exception.chapter.ChapterAlreadyExistsException;
import book.data.service.exception.chapter.ChapterDoesNotExistException;
import book.data.service.manager.chapter.ChapterManager;
import book.data.service.model.chapter.Chapter;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ChapterManagerTest {

    private ChapterManager chapterManager;
    @Mock
    private ChapterDAO chapterDAO;
    @Mock
    private BookDAO bookDAO;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        chapterManager = new ChapterManager(chapterDAO, bookDAO);
    }

    @Test
    public void testGetAllChaptersPaged() {
        when(chapterDAO.getAllChaptersPaged(anyInt(), anyInt())).thenReturn(CHAPTER_LIST);
        List<Chapter> chapterList = chapterManager.getAllChaptersPaged(PAGE_NUMBER, PAGE_SIZE);
        Assertions.assertThat(chapterList).isEqualTo(CHAPTER_LIST);
    }

    @Test
    public void testGetChaptersByBookNamePaged() {
        when(chapterDAO.getChaptersByBookNamePaged(anyString(), anyInt(), anyInt(), anyString()))
            .thenReturn(CHAPTER_LIST);
        List<Chapter> chapterList = chapterManager
            .getChaptersByBookNamePaged(BOOK_NAME, PAGE_NUMBER, PAGE_SIZE, CREATED_BY_ONE);
        Assertions.assertThat(chapterList).isEqualTo(CHAPTER_LIST);
    }

    @Test
    public void testGetChaptersByBookNamePaged_BookDoesNotExistException() {
        when(chapterDAO.getChaptersByBookNamePaged(anyString(), anyInt(), anyInt(), anyString()))
            .thenThrow(new BookDoesNotExistException());
        Assert.assertThrows(BookDoesNotExistException.class, () -> {
            chapterManager.getChaptersByBookNamePaged(BOOK_NAME, PAGE_NUMBER, PAGE_SIZE,
                CREATED_BY_ONE);
        });
    }

    @Test
    public void testGetChapterByBookNameAndChapterNumber() {
        when(chapterDAO.getChapterByBookNameAndChapterNumber(anyString(), anyLong(), anyString()))
            .thenReturn(CHAPTER_ONE);
        Chapter chapter = chapterManager.getChapterByBookNameAndChapterNumber(
            BOOK_NAME,
            CHAPTER_NUMBER_ONE,
            CREATED_BY_ONE
        );
        Assertions.assertThat(chapter).isEqualTo(CHAPTER_ONE);
    }

    @Test
    public void testGetChapterByBookNameAndChapterName_BookDoesNotExistException() {
        when(chapterDAO.getChapterByBookNameAndChapterNumber(anyString(), anyLong(), anyString()))
            .thenThrow(new BookDoesNotExistException());
        Assert.assertThrows(BookDoesNotExistException.class, () -> {
            chapterManager.getChapterByBookNameAndChapterNumber(BOOK_NAME, CHAPTER_NUMBER_ONE,
                CREATED_BY_ONE);
        });
    }

    @Test
    public void testGetChapterByBookNameAndChapterName_ChapterDoesNotExistException() {
        when(chapterDAO.getChapterByBookNameAndChapterNumber(anyString(), anyLong(), anyString()))
            .thenThrow(new ChapterDoesNotExistException());
        Assert.assertThrows(ChapterDoesNotExistException.class, () -> {
            chapterManager.getChapterByBookNameAndChapterNumber(BOOK_NAME, CHAPTER_NUMBER_ONE,
                CREATED_BY_TWO);
        });
    }

    @Test
    public void testCreateChapter() {
        when(bookDAO.getBookByBookName(BOOK_NAME, CREATED_BY_ONE)).thenReturn(BOOK_ONE);
        when(chapterDAO.createChapter(
            any(Book.class),
            anyLong(),
            anyString(),
            anyLong(),
            anyString()
        )).thenReturn(CHAPTER_ONE);
        Chapter chapter = chapterManager.createChapter(
            BOOK_NAME,
            CHAPTER_NUMBER_ONE,
            CHAPTER_NAME_ONE,
            CHAPTER_VIEWS_ONE,
            CREATED_BY_ONE
        );
        Assertions.assertThat(chapter).isEqualTo(CHAPTER_ONE);
    }

    @Test
    public void testCreateChapter_ChapterAlreadyExistsException() {
        when(bookDAO.getBookByBookName(BOOK_NAME, CREATED_BY_ONE)).thenReturn(BOOK_ONE);
        when(chapterDAO.createChapter(
            any(Book.class),
            anyLong(),
            anyString(),
            anyLong(),
            anyString()
        )).thenThrow(new ChapterAlreadyExistsException());
        Assert.assertThrows(ChapterAlreadyExistsException.class, () -> {
            chapterManager.createChapter(
                BOOK_NAME,
                CHAPTER_NUMBER_ONE,
                CHAPTER_NAME_ONE,
                CHAPTER_VIEWS_ONE,
                CREATED_BY_ONE
            );
        });
    }

    @Test
    public void testCreateChapter_BookDoesNotExistException() {
        when(bookDAO.getBookByBookName(BOOK_NAME, CREATED_BY_ONE)).thenThrow(
            new BookDoesNotExistException());
        Assert.assertThrows(BookDoesNotExistException.class, () -> {
            chapterManager.createChapter(
                BOOK_NAME,
                CHAPTER_NUMBER_ONE,
                CHAPTER_NAME_ONE,
                CHAPTER_VIEWS_ONE,
                CREATED_BY_ONE
            );
        });
    }
}
