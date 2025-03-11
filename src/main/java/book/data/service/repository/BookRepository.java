package book.data.service.repository;

import book.data.service.sqlmodel.book.Book;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Book> findAllBooks() {
        String queryString = "SELECT t FROM Book t";
        TypedQuery<Book> query = entityManager.createQuery(queryString, Book.class);
        List<Book> books = query.getResultList();
        return books;
    }

    public List<Book> findBooksByBookNameOnly(String bookName) {
        String queryString = "SELECT b FROM Book b WHERE b.bookName=:bookName";
        TypedQuery<Book> query = entityManager.createQuery(queryString, Book.class);
        query.setParameter("bookName", bookName);
        List<Book> bookList = query.getResultList();
        return bookList;
    }

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

    public Book findBookByBookNumber(Long bookNumber, String createdBy) {
        String queryString = "SELECT b FROM Book b WHERE b.bookNumber=:bookNumber "
            + "AND b.createdBy=:createdBy";
        log.info("queryString: " + queryString);
        log.info("bookNumber: " + bookNumber);
        log.info("createdBy: " + createdBy);
        TypedQuery<Book> query = entityManager.createQuery(queryString, Book.class);
        Book book = query.setParameter("bookNumber", bookNumber)
            .setParameter("createdBy", createdBy)
            .getSingleResult();
        log.info("book: " + book);
        return book;
    }

    public boolean doesBookExist(String bookName, String createdBy) {
        Book book = findBookByBookName(bookName, createdBy);
        if (book == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean doesBookExistWithBookNameOnly(String bookName) {
        List<Book> bookList = findBooksByBookNameOnly(bookName);
        return bookList.size() > 0;
    }

    public boolean doesBookExistWithBookNumber(
        Long bookNumber,
        String createdBy
    ) {
        try {
            log.info("Does book exist with book number");
            log.info("bookNumber: " + bookNumber);
            log.info("createdBy: " + createdBy);
            Book book = findBookByBookNumberOnly(bookNumber);
            return book!=null;
        } catch (NoResultException noResultException) {
            return false;
        }
    }

    public boolean doesBookExistWithOnlyBookNumber(Long bookNumber) {
        try {
            Book book = findBookByBookNumberOnly(bookNumber);
            return book!=null;
        }
        catch (NoResultException noResultException) {
            return false;
        }
    }

    public Book findBookByBookNumberOnly(Long bookNumber) {
        String queryString = "SELECT b FROM Book b WHERE b.bookNumber=:bookNumber";
        TypedQuery<Book> query = entityManager.createQuery(queryString, Book.class);
        Book book = query.setParameter("bookNumber", bookNumber)
            .getSingleResult();
        return book;
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

    public List<Book> findAllBooksSortedByBookViewsPaged(PageRequest pageRequest,
        String createdBy) {
        String queryString = "SELECT t FROM Book t WHERE t.createdBy=:createdBy "
            + "ORDER BY t.bookViews DESC";
        TypedQuery<Book> query = entityManager.createQuery(queryString, Book.class);
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        query.setParameter("createdBy", createdBy);
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        List<Book> results = query.getResultList();
        return results;
    }

    public List<Book> findAllBooksSortedByCreationTimePaged(PageRequest pageRequest,
        String createdBy) {
        String queryString = "SELECT t FROM Book t WHERE t.createdBy=:createdBy "
            + "ORDER BY t.createdEpochMilli DESC";
        TypedQuery<Book> query = entityManager.createQuery(queryString, Book.class);
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        query.setParameter("createdBy", createdBy);
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        List<Book> results = query.getResultList();
        return results;
    }

    public List<Book> findAllBooksSortedByBookNamePaged(PageRequest pageRequest, String createdBy) {
        String queryString = "SELECT t FROM Book t WHERE t.createdBy=:createdBy "
            + "ORDER BY t.bookName ASC";
        TypedQuery<Book> query = entityManager.createQuery(queryString, Book.class);
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        query.setParameter("createdBy", createdBy);
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        List<Book> results = query.getResultList();
        return results;
    }

    public List<Book> findBooksByBookTagPaged(String searchTag, PageRequest pageRequest,
        String createdBy) {
        String nativeQueryString = "SELECT b.* "
            + "FROM book b "
            + "JOIN book_tags bt ON b.book_id = bt.book_tag_id "
            + "WHERE bt.book_tags='{searchTag}' "
            + "AND b.created_by='{createdBy}';";
        nativeQueryString = nativeQueryString.replace("{searchTag}", searchTag);
        nativeQueryString = nativeQueryString.replace("{createdBy}", createdBy);
        Query query = entityManager.createNativeQuery(nativeQueryString, Book.class);
        query.setFirstResult(pageRequest.getPageNumber()* pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        List<Book> resultList = query.getResultList();
        return resultList;
    }

    public List<Book> searchNaturalLanguage(String searchInput) {
        String queryString = "SELECT * FROM Book WHERE MATCH(book_name) AGAINST ('" + searchInput
            + "' IN NATURAL LANGUAGE MODE);";
        Query query = entityManager.createNativeQuery(queryString);
        List<Book> resultList = query.getResultList();
        return resultList;
    }

    @Transactional
    public void saveBook(Book book) {
        log.info("saveBook: " + book);
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
    public void deleteBook(String bookName, String createdBy) {
        String queryString = "DELETE FROM Book b WHERE b.bookName=:bookName AND b.createdBy=:createdBy";
        TypedQuery<Book> query = entityManager.createQuery(queryString, Book.class);
        query.setParameter("bookName", bookName);
        query.setParameter("createdBy", createdBy);
        query.executeUpdate();
    }

    @Transactional
    public void updateBookViews(
        String bookName,
        Long updatedBookViews,
        String createdBy
    ) {
        String queryString = "UPDATE Book b "
            + "SET b.bookViews=:bookViews "
            + "WHERE b.bookName=:bookName AND b.createdBy=:createdBy";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("bookViews", updatedBookViews);
        query.setParameter("bookName", bookName);
        query.setParameter("createdBy", createdBy);
        query.executeUpdate();
    }

    @Transactional
    public void updateBookViewsByBookNumber(
        Long bookNumber,
        Long updatedBookViews,
        String createdBy
    ) {
        String queryString = "UPDATE Book b SET b.bookViews=:bookViews "
            + "WHERE b.bookNumber=:bookNumber AND b.createdBy=:createdBy";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("bookNumber", bookNumber);
        query.setParameter("bookViews", updatedBookViews);
        query.setParameter("createdBy", createdBy);
        query.executeUpdate();
    }

}
