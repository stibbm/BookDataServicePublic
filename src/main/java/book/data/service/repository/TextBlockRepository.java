package book.data.service.repository;

import book.data.service.sqlmodel.textblock.TextBlock;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Repository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TextBlockRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TextBlock> findAllTextBlocks(String createdBy) {
        String queryString = "SELECT tb FROM TextBlock tb WHERE tb.createdBy=:createdBy";
        TypedQuery<TextBlock> query = entityManager.createQuery(queryString, TextBlock.class);
        query.setParameter("createdBy", createdBy);
        List<TextBlock> resultList = query.getResultList();
        return resultList;
    }

    public TextBlock findTextBlockByBookNameAndChapterNumberAndTextBlockNumber(
        String bookName,
        Long chapterNumber,
        Long textBlockNumber,
        String createdBy
    ) {
        String queryString = "SELECT tb FROM TextBlock tb WHERE "
            + "tb.textBlockId.chapter.chapterId.book.bookName=:bookName AND "
            + "tb.textBlockId.chapter.chapterId.chapterNumber=:chapterNumber AND "
            + "tb.textBlockId.textBlockNumber=:textBlockNumber AND "
            + "tb.createdBy=:createdBy";
        TypedQuery<TextBlock> query = entityManager.createQuery(queryString, TextBlock.class);
        query.setParameter("bookName", bookName);
        query.setParameter("chapterNumber", chapterNumber);
        query.setParameter("textBlockNumber", textBlockNumber);
        query.setParameter("createdBy", createdBy);
        TextBlock result = query.getSingleResult();
        return result;
    }

    public List<TextBlock> findTextBlocksByBookNumber(Long bookNumber) {
        String sQuery = "SELECT tb FROM TextBlock tb WHERE tb.id.chapter.id.book.bookNumber=:bookNumber";
        TypedQuery<TextBlock> query = entityManager.createQuery(sQuery, TextBlock.class);
        List<TextBlock> resultList = query.setParameter("bookNumber", bookNumber).getResultList();
        return resultList;
    }

    public boolean doesTextBlockExist(
        String bookName,
        Long chapterNumber,
        Long textBlockNumber,
        String createdBy
    ) {
        try {
            TextBlock textBlock = findTextBlockByBookNameAndChapterNumberAndTextBlockNumber(
                bookName,
                chapterNumber,
                textBlockNumber,
                createdBy
            );
            log.info("doesTextBlockExist: " + textBlock);
            if (textBlock == null) {
                return false;
            }
            return true;
        } catch (NoResultException noResultException) {
            return false;
        }
    }

    public List<TextBlock> findTextBlocksByBookNumberAndChapterNumberPaged(
        Long bookNumber,
        Long chapterNumber,
        PageRequest pageRequest
    ) {
        String queryString = "SELECT tb FROM TextBlock tb "
            + "WHERE tb.textBlockId.chapter.chapterId.book.bookNumber=:bookNumber AND "
            + "tb.textBlockId.chapter.chapterId.chapterNumber=:chapterNumber "
            + "ORDER BY tb.textBlockId.textBlockNumber ASC";
        TypedQuery<TextBlock> query = entityManager.createQuery(queryString, TextBlock.class);
        query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        query.setParameter("bookNumber", bookNumber);
        query.setParameter("chapterNumber", chapterNumber);
        List<TextBlock> resultList = query.getResultList();
        return resultList;
    }


    public List<TextBlock> findTextBlocksByBookNameAndChapterNumberPaged(
        String bookName,
        Long chapterNumber,
        String createdBy,
        PageRequest pageRequest
    ) {
        String queryString = "SELECT tb FROM TextBlock tb "
            + "WHERE tb.textBlockId.chapter.chapterId.book.bookName=:bookName AND "
            + "tb.textBlockId.chapter.chapterId.chapterNumber=:chapterNumber AND "
            + "tb.createdBy=:createdBy ORDER BY tb.textBlockId.textBlockNumber ASC";
        TypedQuery<TextBlock> query = entityManager.createQuery(queryString, TextBlock.class);
        query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        query.setParameter("bookName", bookName);
        query.setParameter("chapterNumber", chapterNumber);
        query.setParameter("createdBy", createdBy);
        List<TextBlock> resultList = query.getResultList();
        return resultList;
    }

    @Transactional
    public void saveTextBlock(TextBlock textBlock) {
        entityManager.persist(textBlock);
    }
}
