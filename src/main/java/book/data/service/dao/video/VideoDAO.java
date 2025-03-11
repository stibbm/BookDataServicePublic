package book.data.service.dao.video;

import book.data.service.exception.book.BookDoesNotExistException;
import book.data.service.exception.ChapterDoesNotExistException;
import book.data.service.repository.BookRepository;
import book.data.service.repository.ChapterRepository;
import book.data.service.repository.VideoRepository;
import book.data.service.sqlmodel.chapter.Chapter;
import book.data.service.sqlmodel.video.Video;
import book.data.service.sqlmodel.video.VideoId;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VideoDAO {
    private VideoRepository videoRepository;
    private BookRepository bookRepository;
    private ChapterRepository chapterRepository;

    @Autowired
    public VideoDAO(
        VideoRepository videoRepository,
        BookRepository bookRepository,
        ChapterRepository chapterRepository
    ) {
        this.videoRepository = videoRepository;
        this.bookRepository = bookRepository;
        this.chapterRepository = chapterRepository;
    }

    public List<Video> getVideosByBookNameAndChapterNumberPaged(
        String bookName,
        Long chapterNumber,
        String createdBy,
        int pageNumber,
        int pageSize
    ) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        if (!bookRepository.doesBookExist(bookName, createdBy)) {
            throw new BookDoesNotExistException();
        }
        if (!chapterRepository.doesChapterExist(bookName, chapterNumber, createdBy)) {
            throw new ChapterDoesNotExistException();
        }
        List<Video> videoList = videoRepository.findVideosByBookNameAndChapterNumberPaged(bookName, chapterNumber, createdBy, pageRequest);
        return videoList;
    }

    public Video getVideoByBookNameAndChapterNumberAndAudioNumber(
        String bookName,
        Long chapterNumber,
        Long audioNumber,
        String createdBy
    ) {
        if (!bookRepository.doesBookExist(bookName, createdBy)) {
            throw new BookDoesNotExistException();
        }
        if (!chapterRepository.doesChapterExist(bookName, chapterNumber, createdBy)) {
            throw new ChapterDoesNotExistException();
        }
        if (!videoRepository.doesVideoExist(bookName, chapterNumber, audioNumber, createdBy)) {
            throw new VideoDoesNotExistException();
        }
        Video video = videoRepository.findVideoByBookNameAndChapterNameAndVideoNumber(
            bookName,
            chapterNumber,
            audioNumber,
            createdBy
        );
        return video;
    }

    public Video createVideo(
        Chapter chapter,
        Long videoNumber,
        String s3Key,
        String s3Bucket,
        String relativeUrl,
        String createdBy,
        String fileType,
        Long createdEpochMilli
    ) {
        if (!bookRepository.doesBookExist(chapter.getChapterId().getBook().getBookName(), createdBy)) {
            throw new BookDoesNotExistException();
        }
        if (!chapterRepository.doesChapterExist(
            chapter.getChapterId().getBook().getBookName(),
            chapter.getChapterId().getChapterNumber(),
            createdBy
        )){
            throw new ChapterDoesNotExistException();
        }
        if (videoRepository.doesVideoExist(
            chapter.getChapterId().getBook().getBookName(),
            chapter.getChapterId().getChapterNumber(),
            videoNumber,
            createdBy
        )){
            throw new VideoAlreadyExistsException();
        }
        VideoId videoId = new VideoId(videoNumber, chapter);
        Video video = new Video(videoId, s3Key, s3Bucket, relativeUrl, createdBy, fileType, createdEpochMilli);
        videoRepository.saveVideo(video);
        return video;
    }
}
