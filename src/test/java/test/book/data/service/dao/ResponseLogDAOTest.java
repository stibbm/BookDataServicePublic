package test.book.data.service.dao;

import static org.mockito.Mockito.when;

import book.data.service.dao.responselog.ResponseLogDAO;
import book.data.service.repository.ResponseLogRepository;
import book.data.service.sqlmodel.response.ResponseLog;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ResponseLogDAOTest {
    public static final Long RESPONSE_LOG_ID_ONE = 1L;
    public static final String REQUEST_PATH_ONE = "/createBook";
    public static final String RESPONSE_BODY_ONE = "gijweogewi";
    public static final Long RESPONSE_TIMESTAMP_ONE = 1000L;
    public static final String RESPONSE_UUID_ONE = "ewighoeihgiow";

    public static final Long RESPONSE_LOG_ID_TWO = 2L;
    public static final String REQUEST_PATH_TWO = "/createChapter";
    public static final String RESPONSE_BODY_TWO = "ghewiognweo";
    public static final Long RESPONSE_TIMESTAMP_TWO = 200L;
    public static final String RESPONSE_UUID_TWO = "oigwheoigwe";

    public static ResponseLog RESPONSE_LOG_ONE = new ResponseLog(
        RESPONSE_LOG_ID_ONE,
        REQUEST_PATH_ONE,
        RESPONSE_BODY_ONE,
        RESPONSE_TIMESTAMP_ONE,
        RESPONSE_UUID_ONE
    );

    public static ResponseLog RESPONSE_LOG_TWO = new ResponseLog(
        RESPONSE_LOG_ID_TWO,
        REQUEST_PATH_TWO,
        RESPONSE_BODY_TWO,
        RESPONSE_TIMESTAMP_TWO,
        RESPONSE_UUID_TWO
    );

    public ImmutableList<ResponseLog> RESPONSE_LOG_LIST =
        ImmutableList.of(RESPONSE_LOG_ONE, RESPONSE_LOG_TWO);

    private ResponseLogDAO responseLogDAO;

    @Mock
    private ResponseLogRepository responseLogRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        responseLogDAO = new ResponseLogDAO(responseLogRepository);
    }

    @Test
    public void testGetAllResponseLogs() {
        when(responseLogRepository.findAllResponseLogs())
            .thenReturn(RESPONSE_LOG_LIST);
        List<ResponseLog> responseLogList = responseLogDAO.getAllResponseLogs();
        Assertions.assertThat(responseLogList).isEqualTo(RESPONSE_LOG_LIST);
    }

    @Test
    public void testGetResponseLogByResponseId() {
        when(responseLogRepository.findResponseLogByResponseId(RESPONSE_LOG_ID_ONE))
            .thenReturn(RESPONSE_LOG_ONE);
        ResponseLog responseLog = responseLogDAO.getResponseLogByResponseId(RESPONSE_LOG_ID_ONE);
        Assertions.assertThat(responseLog).isEqualTo(RESPONSE_LOG_ONE);
    }
}
