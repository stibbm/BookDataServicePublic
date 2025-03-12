package book.data.service.dao.getbookrequestlog;

import book.data.service.repository.GetBookRequestLogRepository;
import book.data.service.sqlmodel.request.GetBookRequestLog;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetBookRequestLogDAO {
    private GetBookRequestLogRepository getBookRequestLogRepository;

    @Autowired
    public GetBookRequestLogDAO(GetBookRequestLogRepository getBookRequestLogRepository) {
        this.getBookRequestLogRepository = getBookRequestLogRepository;
    }

    public List<GetBookRequestLog> getAllGetBookRequestLogsByEmail(String email) {
        return this.getBookRequestLogRepository.findAllGetBookRequestLogsByEmail(email);
    }

    public GetBookRequestLog getGetBookRequestLogByRequestId(String requestId) {
        return getBookRequestLogRepository.findBookRequestLogByRequestId(requestId);
    }

    public GetBookRequestLog createGetBookRequestLog(
        String bookName,
        Long timestamp,
        String email
    ) {
        GetBookRequestLog getBookRequestLog =
            new GetBookRequestLog();
        getBookRequestLog.setBookName(bookName);
        getBookRequestLog.setTimestamp(timestamp);
        getBookRequestLog.setEmail(email);
        getBookRequestLogRepository.saveGetBookRequestLog(getBookRequestLog);
        return getBookRequestLog;
    }
}
