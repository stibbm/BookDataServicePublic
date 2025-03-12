package book.data.service.manager;

import book.data.service.dao.requestlog.RequestLogDAO;
import book.data.service.service.id.IdService;
import book.data.service.service.time.TimeService;
import book.data.service.sqlmodel.request.RequestLog;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RequestLogManager {
    
    private RequestLogDAO requestLogDAO;
    private TimeService timeService;
    private IdService idService;

    @Autowired
    public RequestLogManager(
        RequestLogDAO requestLogDAO,
        TimeService timeService,
        IdService idService
    ) {
        this.requestLogDAO = requestLogDAO;
        this.timeService = timeService;
        this.idService = idService;
    }

    /**
     * Get All Request Logs
     * @return list of request logs
     */
    public List<RequestLog> getAllRequestLogs() {
        return this.requestLogDAO.getAllRequestLogs();
    }

    /**
     * Get Request Log By Request Id
     * @param requestId request id
     * @return RequestLog
     */
    public RequestLog getRequestLogByRequestId(Long requestId) {
        return this.requestLogDAO.getRequestLogByRequestId(requestId);
    }

    /**
     * Create Request Log
     * @param requestPath request path
     * @param requestBody request body
     */
    public RequestLog createRequestLog(
        String requestPath,
        String requestBody
    ) {
        Long timestamp = timeService.getCurrentTimestamp();
        String uuid = idService.generateUuid();
        return requestLogDAO.createRequestLog(requestPath, requestBody, timestamp, uuid);
    }

}
