package book.data.service.repository;

import book.data.service.model.VideoCreationMilestone;
import book.data.service.sqlmodel.payment.PaymentForTranslatedYoutubeBook;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
public class PaymentForTranslatedYoutubeBookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PaymentForTranslatedYoutubeBookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PaymentForTranslatedYoutubeBook findPaymentByBookNumberAndStartChapterAndEndChapter(
        Long bookNumber,
        Long startChapter,
        Long endChapter) {
        String queryString =
            "SELECT p FROM PaymentForTranslatedYoutubeBook p WHERE p.bookNumber=:bookNumber AND "
                + "p.startChapter=:startChapter AND p.endChapter=:endChapter";
        System.out.println("find payment by book number and start chapter and end cahpter");
        TypedQuery<PaymentForTranslatedYoutubeBook> query = entityManager.createQuery(queryString,
            PaymentForTranslatedYoutubeBook.class);
        query.setParameter("bookNumber", bookNumber);
        query.setParameter("startChapter", startChapter);
        query.setParameter("endChapter", endChapter);
        System.out.println("bookNumber = " + bookNumber);
        System.out.println("startChapter = " + startChapter);
        System.out.println("endChapter = " + endChapter);
        PaymentForTranslatedYoutubeBook paymentForTranslatedYoutubeBook = query.getSingleResult();
        return paymentForTranslatedYoutubeBook;
    }

    public PaymentForTranslatedYoutubeBook findPaymentByBookNameAndStartChapterAndEndChapterAndCreatedBy(
        Long bookNumber,
        Long startChapter,
        Long endChapter,
        String createdBy) {
        String queryString =
            "SELECT p FROM PaymentForTranslatedYoutubeBook p WHERE p.bookNumber=:bookNumber AND "
                + "p.startChapter=:startChapter AND p.endChapter=:endChapter AND p.createdBy=:createdBy";
        TypedQuery<PaymentForTranslatedYoutubeBook> query = entityManager.createQuery(queryString,
            PaymentForTranslatedYoutubeBook.class);
        query.setParameter("bookNumber", bookNumber);
        query.setParameter("startChapter", startChapter);
        query.setParameter("endChapter", endChapter);
        query.setParameter("createdBy", createdBy);
        PaymentForTranslatedYoutubeBook paymentForTranslatedYoutubeBook = query.getSingleResult();
        return paymentForTranslatedYoutubeBook;
    }

    public boolean doesPaymentForTranslatedYoutubeBookExist(Long bookNumber, Long startChapter,
        Long endChapter) {
        try {
            PaymentForTranslatedYoutubeBook paymentForTranslatedYoutubeBook = findPaymentByBookNumberAndStartChapterAndEndChapter(
                bookNumber, startChapter, endChapter);
            return paymentForTranslatedYoutubeBook != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    public List<PaymentForTranslatedYoutubeBook> findPayments(String createdBy) {
        String queryString = "SELECT p FROM PaymentForTranslatedYoutubeBook p WHERE p.createdBy = :createdBy";
        TypedQuery<PaymentForTranslatedYoutubeBook> query = entityManager.createQuery(queryString,
            PaymentForTranslatedYoutubeBook.class);
        query.setParameter("createdBy", createdBy);
        return query.getResultList();
    }

    @Transactional
    public void updatePaymentMilestone(
        VideoCreationMilestone videoCreationMilestone,
        Long bookNumber,
        Long startChapter,
        Long endChapter)
    {
        String queryString = "UPDATE PaymentForTranslatedYoutubeBook p " +
            "SET p.videoCreationMilestone=:videoCreationMilestone " +
            "WHERE p.bookNumber=:bookNumber AND p.startChapter=:startChapter AND p.endChapter=:endChapter";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("videoCreationMilestone", videoCreationMilestone);
        query.setParameter("bookNumber", bookNumber);
        query.setParameter("startChapter", startChapter);
        query.setParameter("endChapter", endChapter);
        query.executeUpdate();
        entityManager.flush();
    }

    @org.springframework.transaction.annotation.Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateTranslatedYoutubeBookStatus(
        String newTranslatedYoutubeBookStatus,
        Long bookNumber,
        Long startChapter,
        Long endChapter
    ) {
        log.info("updating translated youtube book status to {} for bookNumber: {}, startChapter: {}, endChapter: {}",
            newTranslatedYoutubeBookStatus, bookNumber, startChapter, endChapter);
        String queryString = "UPDATE PaymentForTranslatedYoutubeBook p "
            + "SET p.translatedYoutubeBookStatus=:translatedYoutubeBookStatus "
            + "WHERE p.bookNumber=:bookNumber AND p.startChapter=:startChapter AND p.endChapter=:endChapter";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("translatedYoutubeBookStatus", newTranslatedYoutubeBookStatus);
        query.setParameter("bookNumber", bookNumber);
        query.setParameter("startChapter", startChapter);
        query.setParameter("endChapter", endChapter);
        log.info(query.toString());
        query.executeUpdate();
        entityManager.flush();
    }

    @org.springframework.transaction.annotation.Transactional(isolation = Isolation.READ_COMMITTED)
    public void savePayment(PaymentForTranslatedYoutubeBook paymentForTranslatedYoutubeBook) {
        entityManager.persist(paymentForTranslatedYoutubeBook);
        entityManager.flush();
    }

}
