package book.data.service.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import book.data.service.sqlmodel.youtubevideo.YouTubeVideo;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class YouTubeVideoRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public YouTubeVideo findYouTubeVideoByYouTubeVideoId(String youTubeVideoId) {
        String sQuery = "SELECT ytv FROM YouTubeVideo ytv WHERE ytv.youtubeVideoId=:youTubeVideoId";
        TypedQuery<YouTubeVideo> query = entityManager.createQuery(sQuery, YouTubeVideo.class);
        YouTubeVideo result = query.setParameter("youTubeVideoId", youTubeVideoId).getSingleResult();
        return result;
    }

    public boolean doesYouTubeVideoExist(String youTubeVideoId) {
        try {
            YouTubeVideo youTubeVideo = findYouTubeVideoByYouTubeVideoId(youTubeVideoId);
            return youTubeVideo!=null;
        }
        catch (NoResultException noResultException) {
            return false;
        }
    }

    public List<YouTubeVideo> findYouTubeVideosByBookNumber(Long bookNumber) {
        String sQuery = "SELECT ytv FROM YouTubeVideo ytv WHERE ytv.bookNumber=:bookNumber ORDER BY ytv.startChapterNumber DESC";
        TypedQuery<YouTubeVideo> query = entityManager.createQuery(sQuery, YouTubeVideo.class);
        List<YouTubeVideo> resultList = query.setParameter("bookNumber", bookNumber)
            .getResultList();
        return resultList;
    }

    @Transactional
    public void saveYouTubeVideo(YouTubeVideo youTubeVideo) {
        entityManager.persist(youTubeVideo);
    }
}
