package book.data.service.manager;

import book.data.service.dao.responselog.ResponseLogDAO;
import book.data.service.service.id.IdService;
import book.data.service.service.time.TimeService;
import book.data.service.sqlmodel.response.ResponseLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResponseLogManager {
    private ResponseLogDAO responseLogDAO;
    private TimeService timeService;
    private IdService idService;

    @Autowired
    public ResponseLogManager(
        ResponseLogDAO responseLogDAO,
        TimeService timeService,
        IdService idService
    ) {
        this.responseLogDAO = responseLogDAO;
        this.timeService = timeService;
        this.idService = idService;
    }

    /**
     * Create Response Log
     * @param requestPath request path
     * @param responseBody response body
     * @param responseUuid response uuid
     * @return ResponseLog
     */
    public ResponseLog createResponseLog(
        String requestPath,
        String responseBody,
        String responseUuid
    ) {
        Long timestamp = timeService.getCurrentTimestamp();
        return responseLogDAO.createResponseLog(
            requestPath,
            responseBody,
            timestamp,
            responseUuid
        );
    }
}
