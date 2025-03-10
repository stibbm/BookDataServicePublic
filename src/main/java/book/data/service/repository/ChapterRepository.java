package book.data.service.repository;

import java.util.List;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import book.data.service.sqlmodel.chapter.Chapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ChapterRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Chapter> findAllChaptersPaged(PageRequest pageRequest) {
        String queryString = "SELECT c FROM Chapter c";
        TypedQuery<Chapter> query = entityManager.createQuery(queryString, Chapter.class);
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        List<Chapter> results = query.getResultList();
        return results;
    }

    public List<Chapter> findChaptersByBookNamePaged(String bookName, PageRequest pageRequest) {
        String queryString = "SELECT c FROM Chapter c WHERE c.chapterId.book.bookName=:bookName";
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

    public boolean doesChapterExist(String bookName, Long chapterNumber, String createdBy) {
        Chapter chapter = findChapterByBookNameAndChapterNumber(bookName, chapterNumber, createdBy);
        return chapter!=null;
    }

    @Transactional
    public void saveChapter(Chapter chapter) {
        this.entityManager.persist(chapter);
    }
}
