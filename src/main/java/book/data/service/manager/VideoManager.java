package book.data.service.manager;

import static book.data.service.constants.Constants.MAX_PAGE_SIZE;
import static book.data.service.constants.Constants.PAGE_ZERO;

import book.data.service.clientwrapper.S3ClientWrapper;
import book.data.service.dao.chapter.ChapterDAO;
import book.data.service.dao.video.VideoDAO;
import book.data.service.service.id.IdService;
import book.data.service.service.time.TimeService;
import book.data.service.sqlmodel.chapter.Chapter;
import book.data.service.sqlmodel.video.Video;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VideoManager {

    private VideoDAO videoDAO;
    private ChapterDAO chapterDAO;
    private S3ClientWrapper s3ClientWrapper;
    private IdService idService;
    private TimeService timeService;

    @Autowired
    public VideoManager(
        VideoDAO videoDAO,
        ChapterDAO chapterDAO,
        S3ClientWrapper s3ClientWrapper,
        IdService idService,
        TimeService timeService
    ) {
        this.videoDAO = videoDAO;
        this.chapterDAO = chapterDAO;
        this.s3ClientWrapper = s3ClientWrapper;
        this.idService = idService;
        this.timeService = timeService;
    }

    public List<Video> getVideosByBookNameAndChapterNumber(
        String bookName,
        Long chapterNumber,
        String createdBy
    ) {
        List<Video> videoList = videoDAO.getVideosByBookNameAndChapterNumberPaged(
            bookName,
            chapterNumber,
            createdBy,
            PAGE_ZERO,
            MAX_PAGE_SIZE
        );
        return videoList;
    }
    
    public Video createVideo(
        String bookName,
        Long chapterNumber,
        Long videoNumber,
        byte[] videoBytes,
        String fileType,
        String s3Bucket,
        String createdBy
    ) throws MalformedURLException, URISyntaxException {
        Chapter chapter = chapterDAO.getChapterByBookNameAndChapterNumber(bookName, chapterNumber,
            createdBy);
        String s3Key = idService.generateVideoS3Key(
            chapter.getChapterId().getBook().getBookNumber(),
            chapterNumber,
            videoNumber,
            fileType
        );
        String relativeUrl = idService.generateRelativeImageUrlFromS3Key(s3Key);
        s3ClientWrapper.createS3File(
            videoBytes,
            s3Bucket,
            s3Key
        );
        Long createdEpochMilli = timeService.getCurrentLocalDateTimeLong();
        Video video = videoDAO.createVideo(
            chapter,
            videoNumber,
            s3Key,
            s3Bucket,
            relativeUrl,
            createdBy,
            fileType,
            createdEpochMilli
        );
        return video;
    }
}
