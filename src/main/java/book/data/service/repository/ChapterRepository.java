package book.data.service.repository;

import book.data.service.sqlmodel.chapter.Chapter;
import book.data.service.sqlmodel.chapter.LockStatus;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ChapterRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Chapter> findAllChaptersPaged(PageRequest pageRequest) {
        String queryString = "SELECT c FROM Chapter c ORDER BY c.chapterId.chapterNumber ASC";
        TypedQuery<Chapter> query = entityManager.createQuery(queryString, Chapter.class);
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        List<Chapter> results = query.getResultList();
        return results;
    }

    public List<Chapter> findChaptersByBookNamePaged(String bookName, PageRequest pageRequest) {
        String queryString = "SELECT c FROM Chapter c WHERE c.chapterId.book.bookName=:bookName "
            + "ORDER BY c.chapterId.chapterNumber ASC";
        TypedQuery<Chapter> query = entityManager.createQuery(queryString, Chapter.class);
        query.setParameter("bookName", bookName);
        query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        List<Chapter> results = query.getResultList();
        return results;
    }

    public Chapter findChapterByBookNameAndChapterNumber(
        String bookName,
        Long chapterNumber,
        String createdBy
    ) {
        String queryString = "SELECT c FROM Chapter c WHERE c.chapterId.book.bookName=:bookName "
            + "AND c.chapterId.chapterNumber=:chapterNumber "
            + "AND c.createdBy=:createdBy";
        TypedQuery<Chapter> query = entityManager.createQuery(queryString, Chapter.class);
        List<Chapter> results = query
            .setParameter("bookName", bookName)
            .setParameter("chapterNumber", chapterNumber)
            .setParameter("createdBy", createdBy)
            .getResultList();
        if (results.isEmpty()) {
            return null;
        }
        Chapter chapter = results.get(0);
        return chapter;
    }

    public Chapter findChapterByBookNameAndChapterName(
        String bookName,
        String chapterName,
        String createdBy
    ) {
        String queryString = "SELECT c FROM Chapter c WHERE c.chapterId.book.bookNumber=:bookNumber "
            + "AND c.chapterName=:chapterName AND c.createdBy=:createdBy";
        TypedQuery<Chapter> query = entityManager.createQuery(queryString, Chapter.class);
        query.setParameter("bookNumber", bookName);
        query.setParameter("chapterName", chapterName);
        query.setParameter("createdBy", createdBy);
        Chapter chapter = query.getSingleResult();
        return chapter;
    }

    public Chapter findChapterByOnlyBookNumberAndChapterNumber(
        Long bookNumber,
        Long chapterNumber
    ) {
        String queryString = "SELECT c FROM Chapter c WHERE c.chapterId.book.bookNumber=:bookNumber "
            + "AND c.chapterId.chapterNumber=:chapterNumber";
        TypedQuery<Chapter> query = entityManager.createQuery(queryString, Chapter.class);
        query.setParameter("bookNumber", bookNumber);
        query.setParameter("chapterNumber", chapterNumber);
        Chapter chapter = query.getSingleResult();
        return chapter;
    }

    public boolean doesChapterExistWithOnlyBookNumberAndChapterNumber(
        Long bookNumber,
        Long chapterNumber
    ) {
        try {
            Chapter chapter = findChapterByOnlyBookNumberAndChapterNumber(bookNumber, chapterNumber);
            return chapter != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean doesChapterExistByBookNameAndChapterName(String bookName, String chapterName, String createdBy) {
        try {
            Chapter chapter = findChapterByBookNameAndChapterName(bookName, chapterName, createdBy);
            return chapter!=null;
        }
        catch (NoResultException nre) {
            return false;
        }
    }

    public boolean doesChapterExist(String bookName, Long chapterNumber, String createdBy) {
        Chapter chapter = findChapterByBookNameAndChapterNumber(bookName, chapterNumber, createdBy);
        return chapter!=null;
    }

    @Transactional
    public void updateLockStatus(Long bookNumber, Long chapterNumber, LockStatus lockStatus) {
        String queryString = "UPDATE Chapter c "
            + "SET c.lockStatus=:lockStatus WHERE "
            + "c.chapterId.book.bookNumber=:bookNumber AND c.chapterId.chapterNumber=:chapterNumber";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("lockStatus", lockStatus);
        query.setParameter("bookNumber", bookNumber);
        query.setParameter("chapterNumber", chapterNumber);
        query.executeUpdate();
    }

    @Transactional
    public void saveChapter(Chapter chapter) {
        this.entityManager.persist(chapter);
    }
}
