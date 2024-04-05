package test.book.data.service.activity.book;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static test.book.data.service.dao.BookDAOTest.BOOK_DESCRIPTION;
import static test.book.data.service.dao.BookDAOTest.BOOK_LANGUAGE;
import static test.book.data.service.dao.BookDAOTest.BOOK_NAME;
import static test.book.data.service.dao.BookDAOTest.BOOK_ONE;
import static test.book.data.service.dao.BookDAOTest.BOOK_TAGS;
import static test.book.data.service.dao.BookDAOTest.BOOK_THUMBNAIL;
import static test.book.data.service.dao.BookDAOTest.BOOK_VIEWS;
import static test.book.data.service.dao.BookDAOTest.CREATED_BY_ONE;

import book.data.service.activity.CreateBookActivity;
import book.data.service.exception.BookAlreadyExistsException;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.BookManager;
import book.data.service.message.book.CreateBookRequest;
import book.data.service.message.book.CreateBookResponse;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CreateBookActivityTest {

    public static final String AUTH_TOKEN = "ogiheohes";
    public static final CreateBookRequest CREATE_BOOK_REQUEST =
        CreateBookRequest.builder()
            .bookName(BOOK_NAME)
            .bookDescription(BOOK_DESCRIPTION)
            .bookLanguage(BOOK_LANGUAGE)
            .bookViews("" + BOOK_VIEWS)
            .bookThumbnail(BOOK_THUMBNAIL)
            .bookTags(BOOK_TAGS)
            .build();
    public static final CreateBookResponse CREATE_BOOK_RESPONSE =
        CreateBookResponse.builder()
            .book(BOOK_ONE)
            .build();

    private CreateBookActivity createBookActivity;
    @Mock
    private BookManager bookManager;
    @Mock
    private FirebaseService firebaseService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        createBookActivity = new CreateBookActivity(
            bookManager,
            firebaseService
        );
    }

    @Test
    public void createBook() {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(bookManager.createBook(
            BOOK_NAME,
            CREATED_BY_ONE,
            BOOK_DESCRIPTION,
            BOOK_LANGUAGE,
            BOOK_VIEWS,
            BOOK_THUMBNAIL,
            BOOK_TAGS
        )).thenReturn(BOOK_ONE);
        CreateBookResponse createBookResponse =
            createBookActivity.createBook(CREATE_BOOK_REQUEST, AUTH_TOKEN);
        Assertions.assertThat(createBookResponse).isEqualTo(CREATE_BOOK_RESPONSE);
    }

    @Test
    public void createBook_BookAlreadyExistsException() {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(bookManager.createBook(
            BOOK_NAME,
            CREATED_BY_ONE,
            BOOK_DESCRIPTION,
            BOOK_LANGUAGE,
            BOOK_VIEWS,
            BOOK_THUMBNAIL,
            BOOK_TAGS
        )).thenThrow(new BookAlreadyExistsException());
        Assert.assertThrows(BookAlreadyExistsException.class, () -> {
            bookManager.createBook(
                BOOK_NAME,
                CREATED_BY_ONE,
                BOOK_DESCRIPTION,
                BOOK_LANGUAGE,
                BOOK_VIEWS,
                BOOK_THUMBNAIL,
                BOOK_TAGS
            );
        });
    }
}

