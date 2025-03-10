package book.data.service.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import book.data.service.sqlmodel.book.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class BookRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Book findBookByBookName(String bookName, String createdBy) {
        String queryString = "SELECT b FROM Book b WHERE b.bookName=:bookName AND b.createdBy=:createdBy";
        TypedQuery<Book> query = entityManager.createQuery(queryString, Book.class);
        List<Book> results = query
            .setParameter("bookName", bookName)
            .setParameter("createdBy", createdBy)
            .getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    public boolean doesBookExist(String bookName, String createdBy) {
        Book book = findBookByBookName(bookName, createdBy);
        if (book == null) {
            return false;
        } else {
            return true;
        }
    }

    public List<Book> findAllBooksPaged(PageRequest pageRequest, String createdBy) {
        String queryString = "SELECT t FROM Book t WHERE t.createdBy=:createdBy";
        TypedQuery<Book> query = entityManager.createQuery(queryString, Book.class);
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        query.setParameter("createdBy", createdBy);
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        List<Book> results = query.getResultList();
        return results;
    }

    @Transactional
    public void saveBook(Book book) {
        entityManager.persist(book);
    }

    @Transactional
    public void updateBookThumbnail(
        String bookName,
        String updatedBookThumbnail
    ) {
        String queryString = "UPDATE Book b "
            + "SET b.bookThumbnail=:bookThumbnail "
            + "WHERE b.bookName=:bookName";
        TypedQuery<Book> query = entityManager.createQuery(queryString, Book.class);
        query.setParameter("bookName", bookName);
        query.setParameter("bookThumbnail", updatedBookThumbnail);
        query.executeUpdate();
    }

    @Transactional
    public void deleteBook(String bookName) {
        String queryString = "DELETE FROM Book b WHERE b.bookName=:bookName";
        TypedQuery<Book> query = entityManager.createQuery(queryString, Book.class);
        query.setParameter("bookName", bookName);
        query.executeUpdate();
    }

    @Transactional
    public void updateBookViews(
        String bookName,
        Long updatedBookViews
    ) {
        String queryString = "UPDATE Book b "
            + "SET b.bookViews=:bookViews "
            + "WHERE b.bookName=:bookName";
        TypedQuery<Book> query = entityManager.createQuery(queryString, Book.class);
        query.setParameter("bookViews", updatedBookViews);
        query.setParameter("bookName", bookName);
        query.executeUpdate();
    }
}