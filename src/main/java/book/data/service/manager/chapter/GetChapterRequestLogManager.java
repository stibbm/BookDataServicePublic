package book.data.service.manager.chapter;

import book.data.service.dao.getchapterrequestlog.GetChapterRequestLogDAO;
import book.data.service.sqlmodel.request.GetChapterRequestLog;
import java.time.Instant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetChapterRequestLogManager {
    private GetChapterRequestLogDAO getChapterRequestLogDAO;

    @Autowired
    public GetChapterRequestLogManager(GetChapterRequestLogDAO getChapterRequestLogDAO) {
        this.getChapterRequestLogDAO = getChapterRequestLogDAO;
    }

    public List<GetChapterRequestLog> getAllGetChapterRequestLogs() {
        return this.getChapterRequestLogDAO.getAllGetChapterRequestLogs();
    }

    public GetChapterRequestLog createGetChapterRequestLog(
        String bookName,
        Long chapterNumber,
        String email
    ) {
        Long timestamp = Instant.now().toEpochMilli();
        return getChapterRequestLogDAO.createGetChapterRequestLog(
            bookName,
            chapterNumber,
            timestamp,
            email
        );
    }
}
