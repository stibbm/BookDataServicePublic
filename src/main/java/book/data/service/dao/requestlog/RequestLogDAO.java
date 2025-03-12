package book.data.service.dao.requestlog;

import book.data.service.repository.RequestLogRepository;
import book.data.service.sqlmodel.request.RequestLog;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RequestLogDAO {
    private RequestLogRepository requestLogRepository;

    @Autowired
    public RequestLogDAO(
        RequestLogRepository requestLogRepository
    ) {
        this.requestLogRepository = requestLogRepository;
    }

    /**
     * Get All Request Logs
     *
     * @return RequestLog lists
     */
    public List<RequestLog> getAllRequestLogs() {
        return this.requestLogRepository.findAllRequestLogs();
    }

    /**
     * Get Request Log By Request Id
     * @param requestId request id
     * @return RequestLog
     */
    public RequestLog getRequestLogByRequestId(Long requestId) {
        return this.requestLogRepository.findRequestLogByRequestId(requestId);
    }

    /**
     * Create Request Log
     * @param requestPath request path
     * @param requestBody request body
     * @param timestamp timestamp
     * @param uuid uuid
     */
    public RequestLog createRequestLog(
        String requestPath,
        String requestBody,
        Long timestamp,
        String uuid
    ) {
        RequestLog requestLog = new RequestLog();
        requestLog.setRequestPath(requestPath);
        requestLog.setRequestBody(requestBody);
        requestLog.setTimestamp(timestamp);
        requestLog.setRequestUuid(uuid);
        requestLogRepository.saveRequestLog(requestLog);
        return requestLog;
    }

}
