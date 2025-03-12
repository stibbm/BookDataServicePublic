package book.data.service.repository;

import book.data.service.sqlmodel.response.ResponseLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Repository
public class ResponseLogRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ResponseLog findResponseLogByResponseId(Long responseId) {
        String queryString = "SELECT r FROM ResponseLog r WHERE r.responseId=:responseId";
        TypedQuery<ResponseLog> query = entityManager.createQuery(queryString,
                ResponseLog.class);
        query.setParameter("responseId", responseId);
        return query.getSingleResult();
    }

    public List<ResponseLog> findAllResponseLogs() {
        String queryString = "SELECT r FROM ResponseLog r";
        TypedQuery<ResponseLog> query = entityManager.createQuery(
                queryString, ResponseLog.class
        );
        return query.getResultList();
    }

    public List<ResponseLog> findResponseLogsByResponseUuid(String responseUuid) {
        String queryString = "SELECT r FROM ResponseLog r WHERE r.responseUuid=:responseUuid";
        TypedQuery<ResponseLog> query = entityManager.createQuery(queryString, ResponseLog.class);
        return query.getResultList();
    }

    @Transactional
    public void saveResponseLog(
            ResponseLog responseLog
    ) {
        entityManager.persist(responseLog);
    }
}
