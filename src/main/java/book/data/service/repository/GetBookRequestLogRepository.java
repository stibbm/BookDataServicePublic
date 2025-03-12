package book.data.service.repository;

import book.data.service.sqlmodel.request.GetBookRequestLog;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class GetBookRequestLogRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public GetBookRequestLog findBookRequestLogByRequestId(String requestId) {
        String queryString = "SELECT g FROM GetBookRequestLog g WHERE g.requestId=:requestId";
        TypedQuery<GetBookRequestLog> query = entityManager.createQuery(queryString, GetBookRequestLog.class);
        query.setParameter("requestId", requestId);
        return query.getSingleResult();
    }

    public List<GetBookRequestLog> findAllGetBookRequestLogsByEmail(String email) {
        String queryString = "SELECT g FROM GetBookRequestLog g WHERE g.email=:email";
        TypedQuery<GetBookRequestLog> query = entityManager.createQuery(queryString, GetBookRequestLog.class);
        query.setParameter("email", email);
        return query.getResultList();
    }

    @Transactional
    public void saveGetBookRequestLog(GetBookRequestLog getBookRequestLog) {
        this.entityManager.persist(getBookRequestLog);
    }

}
