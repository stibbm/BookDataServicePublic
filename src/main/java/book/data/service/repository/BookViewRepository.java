package book.data.service.repository;

import book.data.service.sqlmodel.bookview.BookView;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.List;

@Slf4j
@Repository
public class BookViewRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<BookView> findAllBookViewsByBookNumber(Long bookNumber) {
        String queryString = "SELECT bv FROM BookView bv WHERE bv.bookNumber=:bookNumber";
        TypedQuery<BookView> query = entityManager.createQuery(queryString, BookView.class);
        List<BookView> results = query
            .setParameter("bookNumber", bookNumber)
            .getResultList();
        return results;
    }

    public long countFindAllBookViewsByBookNumber(Long bookNumber) {
        String queryString = "SELECT bv FROM BookView bv WHERE bv.bookNumber=:bookNumber";
        TypedQuery<BookView> query = entityManager.createQuery(queryString, BookView.class);
        long resultCount = query
            .setParameter("bookNumber", bookNumber)
            .getResultList().size();
        return resultCount;
    }

    @Transactional
    public void saveBookView(
        BookView bookView
    ) {
        entityManager.persist(bookView);
    }
}
