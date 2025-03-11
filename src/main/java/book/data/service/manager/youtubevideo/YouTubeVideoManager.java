package book.data.service.manager.youtubevideo;


import book.data.service.dao.youtubevideo.YouTubeVideoDAO;
import book.data.service.service.time.TimeService;
import book.data.service.service.youtube.YouTubeVideoUrlService;
import book.data.service.sqlmodel.youtubevideo.YouTubeVideo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class YouTubeVideoManager {
    private YouTubeVideoDAO youTubeVideoDAO;
    private YouTubeVideoUrlService youTubeVideoUrlService;
    private TimeService timeService;

    @Autowired
    public YouTubeVideoManager(YouTubeVideoDAO youTubeVideoDAO, YouTubeVideoUrlService youTubeVideoUrlService, TimeService timeService) {
        this.youTubeVideoDAO = youTubeVideoDAO;
        this.youTubeVideoUrlService = youTubeVideoUrlService;
        this.timeService = timeService;
    }

    public YouTubeVideo getYouTubeVideoByYouTubeVideoId(String youTubeVideoId) {
        return this.youTubeVideoDAO.getYouTubeVideoByYouTubeVideoId(youTubeVideoId);
    }

    public List<YouTubeVideo> getYouTubeVideosByBookNumber(Long bookNumber) {
        return this.youTubeVideoDAO.getYouTubeVideosByBookNumber(bookNumber);
    }

    public boolean doesYouTubeVideoExist(String videoId) {
        return youTubeVideoDAO.doesYouTubeVideoExist(videoId);
    }

    public YouTubeVideo createYouTubeVideo(
            String youTubeVideoId,
            Long bookNumber,
            Long startChapterNumber,
            Long endChapterNumber,
            String requestedBy
    ) {
        log.info("Create Youtube Video: " + youTubeVideoId + "," + bookNumber  + "," + startChapterNumber  + "," + endChapterNumber + "," + requestedBy);
        String youTubeVideoUrl = youTubeVideoUrlService.getYouTubeVideoUrl(youTubeVideoId);
        Long createdEpochMilli = timeService.getCurrentLocalDateTimeLong();
        YouTubeVideo youTubeVideo = youTubeVideoDAO.createYouTubeVideo(
                youTubeVideoId,
                bookNumber,
                startChapterNumber,
                endChapterNumber,
                youTubeVideoUrl,
                requestedBy,
                createdEpochMilli
        );
        log.info("youtube video created: " + youTubeVideo.toString());
        return youTubeVideo;
    }

}
