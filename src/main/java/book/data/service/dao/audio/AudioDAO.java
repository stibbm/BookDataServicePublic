package book.data.service.dao.audio;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import book.data.service.exception.BookDoesNotExistException;
import book.data.service.exception.ChapterDoesNotExistException;
import book.data.service.repository.AudioRepository;
import book.data.service.repository.BookRepository;
import book.data.service.repository.ChapterRepository;
import book.data.service.sqlmodel.audio.Audio;
import book.data.service.sqlmodel.audio.AudioId;
import book.data.service.sqlmodel.chapter.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AudioDAO {

    private AudioRepository audioRepository;
    private BookRepository bookRepository;
    private ChapterRepository chapterRepository;

    @Autowired
    public AudioDAO(
        AudioRepository audioRepository,
        BookRepository bookRepository,
        ChapterRepository chapterRepository
    ) {
        this.audioRepository = audioRepository;
        this.bookRepository = bookRepository;
        this.chapterRepository = chapterRepository;
    }

    public List<Audio> getAudiosByBookNameAndChapterNumber(
        String bookName,
        Long chapterNumber,
        String createdBy
    ) {
        if (!bookRepository.doesBookExist(bookName, createdBy)) {
            throw new BookDoesNotExistException();
        }
        if (!chapterRepository.doesChapterExist(bookName, chapterNumber, createdBy)) {
            throw new ChapterDoesNotExistException();
        }
        List<Audio> audioList = audioRepository.findAudiosByBookNameAndChapterNumber(
            bookName,
            chapterNumber,
            createdBy
        );
        return audioList;
    }

    public Audio getAudioByBookNameAndChapterNumberAndAudioNumber(
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
        if (!audioRepository.doesAudioExist(bookName, chapterNumber, audioNumber, createdBy)) {
            throw new AudioDoesNotExistException();
        }
        Audio audio = audioRepository.findAudioByBookNameAndChapterNumberAndAudioNumber(
            bookName,
            chapterNumber,
            audioNumber,
            createdBy
        );
        return audio;
    }

    public Audio createAudio(
        Chapter chapter,
        Long audioNumber,
        String s3Key,
        String s3Bucket,
        String relativeUrl,
        String createdBy,
        String fileType,
        Long createdEpochMilli
    ) {
        if (!bookRepository.doesBookExist(chapter.getChapterId().getBook().getBookName(),
            createdBy)) {
            throw new BookDoesNotExistException();
        }
        if (!chapterRepository.doesChapterExist(chapter.getChapterId().getBook().getBookName(),
            chapter.getChapterId().getChapterNumber(), createdBy)) {
            throw new ChapterDoesNotExistException();
        }
        if (audioRepository.doesAudioExist(
            chapter.getChapterId().getBook().getBookName(),
            chapter.getChapterId().getChapterNumber(),
            audioNumber,
            createdBy
        )) {
            throw new AudioAlreadyExistsException();
        }
        AudioId audioId = new AudioId(audioNumber, chapter);
        Audio audio = new Audio(audioId, s3Key, s3Bucket, relativeUrl, createdBy, fileType,
            createdEpochMilli);
        audioRepository.saveAudio(audio);
        return audio;
    }
}
