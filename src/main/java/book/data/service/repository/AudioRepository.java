package book.data.service.repository;

import book.data.service.sqlmodel.audio.Audio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class AudioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Audio> findAudiosByBookNameAndChapterNumber(
        String bookName,
        Long chapterNumber,
        String createdBy
    ) {
        String queryString = "SELECT a FROM Audio a "
            + "WHERE a.audioId.chapter.chapterId.book.bookName=:bookName AND "
            + "a.audioId.chapter.chapterId.chapterNumber=:chapterNumber AND "
            + "a.createdBy=:createdBy ORDER BY a.audioId.audioNumber ASC";
        TypedQuery<Audio> query = entityManager.createQuery(queryString, Audio.class);
        List<Audio> resultList = query
            .setParameter("bookName", bookName)
            .setParameter("chapterNumber", chapterNumber)
            .setParameter("createdBy", createdBy)
            .getResultList();
        return resultList;
    }

    public Audio findAudioByBookNameAndChapterNumberAndAudioNumber(
        String bookName,
        Long chapterNumber,
        Long audioNumber,
        String createdBy
    ) {
        String queryString = "SELECT a FROM Audio a "
            + "WHERE a.audioId.chapter.chapterId.book.bookName=:bookName AND "
            + "a.audioId.chapter.chapterId.chapterNumber=:chapterNumber AND "
            + "a.audioId.audioNumber=:audioNumber AND "
            + "a.createdBy=:createdBy";
        TypedQuery<Audio> query = entityManager.createQuery(queryString, Audio.class);
        Audio result = query.setParameter("bookName", bookName)
            .setParameter("chapterNumber", chapterNumber)
            .setParameter("audioNumber", audioNumber)
            .setParameter("createdBy", createdBy)
            .getSingleResult();
        return result;
    }

    public boolean doesAudioExist(
        String bookName,
        Long chapterNumber,
        Long audioNumber,
        String createdBy
    ) {
        try {
            Audio audio = findAudioByBookNameAndChapterNumberAndAudioNumber(
                bookName,
                chapterNumber,
                audioNumber,
                createdBy
            );
            if (audio == null) {
                return false;
            }
            return true;
        } catch (NoResultException noResultException) {
            return false;
        }
    }

    @Transactional
    public void saveAudio(Audio audio) {
        entityManager.persist(audio);
    }
}
