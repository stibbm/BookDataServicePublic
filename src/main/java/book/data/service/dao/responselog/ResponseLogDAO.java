package book.data.service.dao.responselog;


import book.data.service.repository.ResponseLogRepository;
import book.data.service.sqlmodel.response.ResponseLog;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResponseLogDAO {
    private ResponseLogRepository responseLogRepository;

    @Autowired
    public ResponseLogDAO(ResponseLogRepository responseLogRepository) {
        this.responseLogRepository = responseLogRepository;
    }

    /**
     * Get All Response Logs
     * @return ResponseLog list
     */
    public List<ResponseLog> getAllResponseLogs() {
        return this.responseLogRepository.findAllResponseLogs();
    }

    /**
     * Get Response Logs By Response Id
     * @param responseId response id
     * @return ResponseLog
     */
    public ResponseLog getResponseLogByResponseId(Long responseId) {
        return this.responseLogRepository.findResponseLogByResponseId(responseId);
    }

    /**
     * Create Response Log
     * @param requestPath request path
     * @param responseBody response body
     * @param timestamp timestamp
     * @param responseUuid response uuid
     * @return ResponseLog
     */
    public ResponseLog createResponseLog(
        String requestPath,
        String responseBody,
        Long timestamp,
        String responseUuid
    ) {
        ResponseLog responseLog = new ResponseLog();
        responseLog.setRequestPath(requestPath);
        responseLog.setResponseBody(responseBody);
        responseLog.setTimestamp(timestamp);
        responseLog.setResponseUuid(responseUuid);
        responseLogRepository.saveResponseLog(responseLog);
        return responseLog;
    }
}
