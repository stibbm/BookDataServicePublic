package book.data.service.repository;

import book.data.service.sqlmodel.request.GetChapterRequestLog;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class GetChapterRequestLogRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<GetChapterRequestLog> findAllGetChapterRequestLogs() {
        String queryString = "SELECT g FROM GetChapterRequestLog g";
        TypedQuery<GetChapterRequestLog> query = entityManager.createQuery(queryString, GetChapterRequestLog.class);
        return query.getResultList();
    }

    public GetChapterRequestLog findChapterRequestLogByRequestId(Long requestId) {
        String queryString = "SELECT g FROM GetChapterRequestLog g WHERE g.requestId=:requestId";
        TypedQuery<GetChapterRequestLog> query = entityManager.createQuery(queryString,
            GetChapterRequestLog.class);
        query.setParameter("requestId", requestId);
        return query.getSingleResult();
    }

    @Transactional
    public void saveChapterRequestLog(
        GetChapterRequestLog getChapterRequestLog
    ) {
        entityManager.persist(getChapterRequestLog);
    }
}
