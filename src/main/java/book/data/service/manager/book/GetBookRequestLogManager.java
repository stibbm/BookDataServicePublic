package book.data.service.manager.book;

import book.data.service.dao.getbookrequestlog.GetBookRequestLogDAO;
import book.data.service.sqlmodel.request.GetBookRequestLog;
import java.time.Instant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetBookRequestLogManager {
    private GetBookRequestLogDAO getBookRequestLogDAO;

    @Autowired
    public GetBookRequestLogManager(
        GetBookRequestLogDAO getBookRequestLogDAO
    ) {
        this.getBookRequestLogDAO = getBookRequestLogDAO;
    }

    public List<GetBookRequestLog> getAllGetBookRequestLogsByEmail(String email) {
        return this.getBookRequestLogDAO.getAllGetBookRequestLogsByEmail(email);
    }

    public GetBookRequestLog createGetBookRequestLog(
        String bookName,
        String email
    ) {
        Long timestamp = Instant.now().toEpochMilli();
        return getBookRequestLogDAO.createGetBookRequestLog(bookName, timestamp, email);
    }
}
