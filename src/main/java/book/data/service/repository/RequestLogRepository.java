package book.data.service.repository;

import book.data.service.sqlmodel.request.RequestLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Repository
public class RequestLogRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public RequestLog findRequestLogByRequestId(Long requestId) {
        String queryString = "SELECT r FROM RequestLog r WHERE r.requestId=:requestId";
        TypedQuery<RequestLog> query = entityManager.createQuery(queryString,
                RequestLog.class);
        query.setParameter("requestId", requestId);
        return query.getSingleResult();
    }

    public List<RequestLog> findAllRequestLogs() {
        String queryString = "SELECT r FROM RequestLog r";
        TypedQuery<RequestLog> query = entityManager.createQuery(queryString,
                RequestLog.class);
        return query.getResultList();
    }

    public List<RequestLog> findRequestLogsByRequestUuid(String requestUuid) {
        String queryString = "SELECT r FROM RequestLog r WHERE r.requestUuid=:requestUuid";
        TypedQuery<RequestLog> query = entityManager.createQuery(queryString, RequestLog.class);
        query.setParameter("requestUuid", requestUuid);
        return query.getResultList();
    }

    @Transactional
    public void saveRequestLog(
            RequestLog requestLog
    ) {
        entityManager.persist(requestLog);
    }
}
