package book.data.service.dao.youtubevideo;

import book.data.service.exception.BookDoesNotExistException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import book.data.service.exception.book.BookDoesNotExistException;
import book.data.service.exception.youtubevideo.YouTubeVideoDoesNotExistException;
import book.data.service.repository.BookRepository;
import book.data.service.repository.ChapterRepository;
import book.data.service.repository.YouTubeVideoRepository;
import book.data.service.sqlmodel.youtubevideo.YouTubeVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class YouTubeVideoDAO {

    private YouTubeVideoRepository youTubeVideoRepository;
    private BookRepository bookRepository;

    @Autowired
    public YouTubeVideoDAO(YouTubeVideoRepository youTubeVideoRepository,
        BookRepository bookRepository) {
        this.youTubeVideoRepository = youTubeVideoRepository;
        this.bookRepository = bookRepository;
    }

    public YouTubeVideo getYouTubeVideoByYouTubeVideoId(String youTubeVideoId) {
        if (!youTubeVideoRepository.doesYouTubeVideoExist(youTubeVideoId)) {
            throw new YouTubeVideoDoesNotExistException();
        }
        return youTubeVideoRepository.findYouTubeVideoByYouTubeVideoId(youTubeVideoId);
    }

    public List<YouTubeVideo> getYouTubeVideosByBookNumber(Long bookNumber) {
        if (!bookRepository.doesBookExistWithOnlyBookNumber(bookNumber)) {
            throw new BookDoesNotExistException();
        }
        return youTubeVideoRepository.findYouTubeVideosByBookNumber(bookNumber);
    }

    public boolean doesYouTubeVideoExist(String youTubeVideoId) {
        return youTubeVideoRepository.doesYouTubeVideoExist(youTubeVideoId);
    }

    public YouTubeVideo createYouTubeVideo(
        String youTubeVideoId,
        Long bookNumber,
        Long startChapterNumber,
        Long endChapterNumber,
        String youTubeVideoUrl,
        String requestedBy,
        Long createdEpochMilli
    ) {
        log.info("Create Youtube Video: " + youTubeVideoId + "," + bookNumber  + "," + startChapterNumber  + "," + endChapterNumber + "," + youTubeVideoUrl + ","  + requestedBy + "," + createdEpochMilli);
        if (!bookRepository.doesBookExistWithOnlyBookNumber(bookNumber)) {
            throw new BookDoesNotExistException();
        }
        YouTubeVideo youTubeVideo = YouTubeVideo.buildYoutubeVideo(
            youTubeVideoId, bookNumber, startChapterNumber, endChapterNumber, youTubeVideoUrl,
            requestedBy, createdEpochMilli
        );
        youTubeVideoRepository.saveYouTubeVideo(youTubeVideo);
        youTubeVideo = youTubeVideoRepository.findYouTubeVideoByYouTubeVideoId(
            youTubeVideo.getYoutubeVideoId());
        log.info("created youtube video: " + youTubeVideo);
        return youTubeVideo;
    }
}
