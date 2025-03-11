package book.data.service.repository;

import book.data.service.sqlmodel.checkoutsession.CheckoutSession;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class CheckoutSessionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public CheckoutSession findCheckoutSessionBySessionId(String sessionId, String createdBy) {
        String sQuery = "SELECT cs FROM CheckoutSession cs "
            + "WHERE cs.stripeSessionId=:sessionId AND "
            + "cs.createdBy=:createdBy";
        TypedQuery<CheckoutSession> query = entityManager.createQuery(sQuery, CheckoutSession.class);
        return query
            .setParameter("sessionId", sessionId)
            .setParameter("createdBy", createdBy)
            .getSingleResult();
    }

    public CheckoutSession findCheckoutSessionByCheckoutSessionNumber(Long checkoutSessionNumber, String createdBy) {
        String sQuery = "SELECT cs FROM CheckoutSession cs "
            + "WHERE cs.checkoutSessionNumber=:checkoutSessionNumber "
            + "AND cs.createdBy=:createdBy";
        TypedQuery<CheckoutSession> query = entityManager.createQuery(sQuery, CheckoutSession.class);
        CheckoutSession checkoutSession = query.setParameter("checkoutSessionNumber", checkoutSessionNumber)
            .setParameter("createdBy", createdBy)
            .getSingleResult();
        return checkoutSession;
    }

    public boolean doesCheckoutSessionExistByCheckoutSessionNumber(
        Long checkoutSessionNumber,
        String createdBy
    ) {
        try {
            CheckoutSession checkoutSession = findCheckoutSessionByCheckoutSessionNumber(
                checkoutSessionNumber,
                createdBy
            );
            return checkoutSession != null;
        }
        catch (NoResultException noResultException) {
            return false;
        }
    }

    @Transactional
    public void saveCheckoutSession(
        CheckoutSession checkoutSession
    ) {
        entityManager.persist(checkoutSession);
    }
}
