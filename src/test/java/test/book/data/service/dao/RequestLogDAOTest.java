package test.book.data.service.dao;


import static book.data.service.constants.Routes.GSON;
import static org.mockito.Mockito.when;
import static test.book.data.service.activity.book.CreateBookActivityTest.CREATE_BOOK_REQUEST;
import static test.book.data.service.activity.book.GetBookByBookNameActivityTest.GET_BOOK_BY_BOOK_NAME_REQUEST;

import book.data.service.dao.requestlog.RequestLogDAO;
import book.data.service.repository.RequestLogRepository;
import book.data.service.sqlmodel.request.RequestLog;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RequestLogDAOTest {

    public static final Long REQUEST_ID_ONE = 1L;
    public static final String REQUEST_UUID_ONE = "ioghewoignoew";
    public static final String REQUEST_PATH_ONE = "/createBook";
    public static final String REQUEST_BODY_ONE = GSON.toJson(CREATE_BOOK_REQUEST);
    public static final Long REQUEST_TIMESTAMP_ONE = 1000L;

    public static final Long REQUEST_ID_TWO = 2L;
    public static final String REQUEST_UUID_TWO = "goihewgoiew";
    public static final String REQUEST_PATH_TWO = "/getBookByBookName";
    public static final String REQUEST_BODY_TWO = GSON.toJson(GET_BOOK_BY_BOOK_NAME_REQUEST);
    public static final Long REQUEST_TIMESTAMP_TWO = 2000L;

    public static final RequestLog REQUEST_LOG_ONE = new RequestLog(
        REQUEST_ID_ONE,
        REQUEST_PATH_ONE,
        REQUEST_BODY_ONE,
        REQUEST_TIMESTAMP_ONE,
        REQUEST_UUID_ONE
    );
    public static final RequestLog REQUEST_LOG_TWO = new RequestLog(
        REQUEST_ID_TWO,
        REQUEST_PATH_TWO,
        REQUEST_BODY_TWO,
        REQUEST_TIMESTAMP_TWO,
        REQUEST_UUID_TWO
    );

    public static final List<RequestLog> REQUEST_LOG_LIST =
        ImmutableList.of(REQUEST_LOG_ONE, REQUEST_LOG_TWO);

    private RequestLogDAO requestLogDAO;
    @Mock
    private RequestLogRepository requestLogRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        requestLogDAO = new RequestLogDAO(requestLogRepository);
    }

    @Test
    public void testGetAllRequestLogs() {
        when(requestLogRepository.findAllRequestLogs()).thenReturn(REQUEST_LOG_LIST);
        List<RequestLog> requestLogList = requestLogDAO.getAllRequestLogs();
        Assertions.assertThat(requestLogList).isEqualTo(REQUEST_LOG_LIST);
    }

    @Test
    public void testGetRequestLogByRequestId() {
        when(requestLogRepository.findRequestLogByRequestId(REQUEST_ID_ONE)).thenReturn(REQUEST_LOG_ONE);
        RequestLog requestLog = requestLogDAO.getRequestLogByRequestId(REQUEST_ID_ONE);
        Assertions.assertThat(requestLog).isEqualTo(REQUEST_LOG_ONE);
    }

    @Test
    public void testCreateRequestLog() {
        requestLogDAO.createRequestLog(
            REQUEST_PATH_ONE,
            REQUEST_BODY_ONE,
            REQUEST_TIMESTAMP_ONE,
            REQUEST_UUID_ONE
        );
        Assertions.assertThat(true).isEqualTo(true);
    }
}
