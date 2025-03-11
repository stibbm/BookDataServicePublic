package book.data.service.repository;

import book.data.service.sqlmodel.video.Video;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class VideoRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Video findVideoByBookNameAndChapterNameAndVideoNumber(
        String bookName,
        Long chapterNumber,
        Long videoNumber,
        String createdBy
    ) {
        String queryString = "SELECT v FROM Video v WHERE "
            + "v.videoId.chapter.chapterId.book.bookName=:bookName AND "
            + "v.videoId.chapter.chapterId.chapterNumber=:chapterNumber AND "
            + "v.videoId.videoNumber=:videoNumber AND "
            + "v.createdBy=:createdBy";
        TypedQuery<Video> query = entityManager.createQuery(queryString, Video.class);
        Video video = query.setParameter("bookName", bookName)
            .setParameter("chapterNumber", chapterNumber)
            .setParameter("videoNumber", videoNumber)
            .setParameter("createdBy", createdBy)
            .getSingleResult();
        return video;
    }

    public boolean doesVideoExist(
        String bookName,
        Long chapterNumber,
        Long videoNumber,
        String createdBy
    ) {
        try {
            Video video = findVideoByBookNameAndChapterNameAndVideoNumber(
                bookName,
                chapterNumber,
                videoNumber,
                createdBy
            );
            if (video == null) {
                return false;
            }
            return true;
        } catch(NoResultException noResultException) {
            return false;
        }
    }

    public List<Video> findVideosByBookNameAndChapterNumberPaged(
        String bookName,
        Long chapterNumber,
        String createdBy,
        PageRequest pageRequest
    ) {
        String queryString = "SELECT v FROM Video v WHERE " +
            "v.videoId.chapter.chapterId.book.bookName=:bookName AND "
            + "v.videoId.chapter.chapterId.chapterNumber=:chapterNumber AND "
            + "v.createdBy=:createdBy";
        TypedQuery<Video> query = entityManager.createQuery(queryString, Video.class);
        List<Video> resultList = query.setParameter("bookName", bookName)
            .setParameter("chapterNumber", chapterNumber)
            .setParameter("createdBy", createdBy)
            .setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
            .setMaxResults(pageRequest.getPageSize())
            .getResultList();
        return resultList;
    }

    @Transactional
    public void saveVideo(Video video) {
        this.entityManager.persist(video);
    }
}
