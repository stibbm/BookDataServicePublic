package book.data.service.manager.audio;

import book.data.service.clientwrapper.S3ClientWrapper;
import book.data.service.dao.audio.AudioDAO;
import book.data.service.dao.chapter.ChapterDAO;
import book.data.service.service.id.IdService;
import book.data.service.service.time.TimeService;
import book.data.service.sqlmodel.audio.Audio;
import book.data.service.sqlmodel.chapter.Chapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@Service
public class AudioManager {
    private AudioDAO audioDAO;
    private ChapterDAO chapterDAO;
    private S3ClientWrapper s3ClientWrapper;
    private IdService idService;
    private TimeService timeService;

    @Autowired
    public AudioManager(
            AudioDAO audioDAO,
            ChapterDAO chapterDAO,
            S3ClientWrapper s3ClientWrapper,
            IdService idService,
            TimeService timeService
    ) {
        this.audioDAO = audioDAO;
        this.chapterDAO = chapterDAO;
        this.s3ClientWrapper = s3ClientWrapper;
        this.idService = idService;
        this.timeService = timeService;
    }

    public List<Audio> getAudiosByBookNameAndChapterNumber(
            String bookName,
            Long chapterNumber,
            String createdBy
    ) {
        return audioDAO.getAudiosByBookNameAndChapterNumber(
                bookName,
                chapterNumber,
                createdBy
        );
    }

    public Audio getAudioByBookNameAndChapterNumberAndAudioNumber(
            String bookName,
            Long chapterNumber,
            Long audioNumber,
            String createdBy
    ) {
        return audioDAO.getAudioByBookNameAndChapterNumberAndAudioNumber(
                bookName,
                chapterNumber,
                audioNumber,
                createdBy
        );
    }

    public Audio createAudio(
            String bookName,
            Long chapterNumber,
            Long audioNumber,
            byte[] fileBytes,
            String fileType,
            String s3Bucket,
            String createdBy
    ) throws MalformedURLException, URISyntaxException {
        Chapter chapter = chapterDAO.getChapterByBookNameAndChapterNumber(
                bookName, chapterNumber, createdBy
        );
        String s3Key = idService.generateAudioS3Key(
                chapter.getChapterId().getBook().getBookNumber(),
                chapterNumber,
                audioNumber,
                fileType
        );
        String relativeUrl = idService.generateRelativeImageUrlFromS3Key(s3Key);
        s3ClientWrapper.createS3File(
                fileBytes,
                s3Bucket,
                s3Key
        );
        Long createdEpochMilli = timeService.getCurrentLocalDateTimeLong();
        Audio audio = audioDAO.createAudio(
                chapter,
                audioNumber,
                s3Key,
                s3Bucket,
                relativeUrl,
                createdBy,
                fileType,
                createdEpochMilli
        );
        return audio;
    }
}
