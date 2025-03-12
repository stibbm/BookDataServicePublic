package book.data.service.dao.getchapterrequestlog;

import book.data.service.repository.GetChapterRequestLogRepository;
import book.data.service.sqlmodel.request.GetChapterRequestLog;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetChapterRequestLogDAO {
    private GetChapterRequestLogRepository getChapterRequestLogRepository;

    @Autowired
    public GetChapterRequestLogDAO(
        GetChapterRequestLogRepository getChapterRequestLogRepository
    ) {
        this.getChapterRequestLogRepository = getChapterRequestLogRepository;
    }

    public List<GetChapterRequestLog> getAllGetChapterRequestLogs() {
        return this.getChapterRequestLogRepository.findAllGetChapterRequestLogs();
    }

    public GetChapterRequestLog createGetChapterRequestLog(
        String bookName,
        Long chapterNumber,
        Long timestamp,
        String email
    ) {
        GetChapterRequestLog getChapterRequestLog = new GetChapterRequestLog();
        getChapterRequestLog.setBookName(bookName);
        getChapterRequestLog.setChapterNumber(chapterNumber);
        getChapterRequestLog.setTimestamp(timestamp);
        getChapterRequestLog.setEmail(email);
        getChapterRequestLogRepository.saveChapterRequestLog(getChapterRequestLog);
        return getChapterRequestLog;
    }
}
