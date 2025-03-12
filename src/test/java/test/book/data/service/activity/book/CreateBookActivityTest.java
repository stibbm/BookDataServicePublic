package test.book.data.service.activity.book;

import book.data.service.message.book.CreateBookRequest;
import com.google.common.collect.ImmutableList;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CreateBookActivityTest {

    public static final Long REQUEST_ID_ONE = 1L;
    public static final Long TIMESTAMP_ONE = 10000L;
    public static final String EMAIL_ONE = "ogheioago@gmail.com";

    public static final Long REQUEST_ID_TWO = 2L;
    public static final Long TIMESTAMP_TWO = 20000L;
    public static final String EMAIL_TWO = "ogheioawegewgo@gmail.com";

    public static final CreateBookRequest CREATE_BOOK_REQUEST =
            CreateBookRequest.builder()
                    .bookName(BOOK_NAME)
                    .bookDescription(BOOK_DESCRIPTION)
                    .bookLanguage(BOOK_LANGUAGE)
                    .bookViews("" + BOOK_VIEWS)
                    .bookTags(BOOK_TAGS)
                    .bookThumbnailImageBytes(BYTES_ONE)
                    .fileType(FILE_TYPE_ONE)
                    .build();

    public static final GetBookRequestLog GET_BOOK_REQUEST_LOG_ONE =
            new GetBookRequestLog(
                    REQUEST_ID_ONE,
                    BOOK_NAME,
                    TIMESTAMP_ONE,
                    EMAIL_ONE
            );
    public static final GetBookRequestLog GET_BOOK_REQUEST_LOG_TWO =
            new GetBookRequestLog(
                    REQUEST_ID_TWO,
                    BOOK_NAME_TWO,
                    TIMESTAMP_TWO,
                    EMAIL_TWO
            );
    public static final List<GetBookRequestLog> GET_BOOK_REQUEST_LOG_LIST =
            ImmutableList.of(GET_BOOK_REQUEST_LOG_ONE, GET_BOOK_REQUEST_LOG_TWO);
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
    public void createBook() throws MalformedURLException, URISyntaxException {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(bookManager.createBook(
                BOOK_NAME,
                CREATED_BY_ONE,
                BOOK_DESCRIPTION,
                BOOK_LANGUAGE,
                BOOK_VIEWS,
                BOOK_TAGS,
                BYTES_ONE,
                FILE_TYPE_ONE
        )).thenReturn(BOOK_ONE);
        CreateBookResponse createBookResponse =
                createBookActivity.createBook(CREATE_BOOK_REQUEST, AUTH_TOKEN);
        Assertions.assertThat(createBookResponse).isEqualTo(CREATE_BOOK_RESPONSE);
    }

    @Test
    public void createBook_BookAlreadyExistsException()
            throws MalformedURLException, URISyntaxException {
        when(firebaseService.getEmail(anyString())).thenReturn(CREATED_BY_ONE);
        when(bookManager.createBook(
                BOOK_NAME,
                CREATED_BY_ONE,
                BOOK_DESCRIPTION,
                BOOK_LANGUAGE,
                BOOK_VIEWS,
                BOOK_TAGS,
                BYTES_ONE,
                FILE_TYPE_ONE
        )).thenThrow(new BookAlreadyExistsException());
        Assert.assertThrows(BookAlreadyExistsException.class, () -> {
            bookManager.createBook(
                    BOOK_NAME,
                    CREATED_BY_ONE,
                    BOOK_DESCRIPTION,
                    BOOK_LANGUAGE,
                    BOOK_VIEWS,
                    BOOK_TAGS,
                    BYTES_ONE,
                    FILE_TYPE_ONE
            );
        });
    }
}
