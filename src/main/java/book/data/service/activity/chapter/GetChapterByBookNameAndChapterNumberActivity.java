package book.data.service.activity.chapter;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_CHAPTER_BY_BOOK_NAME_AND_CHAPTER_NUMBER;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.chapter.ChapterManager;
import book.data.service.manager.chapter.GetChapterRequestLogManager;
import book.data.service.message.chapter.GetChapterByBookNameAndChapterNumberRequest;
import book.data.service.message.chapter.GetChapterByBookNameAndChapterNumberResponse;
import book.data.service.sqlmodel.chapter.Chapter;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GetChapterByBookNameAndChapterNumberActivity {
    private ChapterManager chapterManager;
    private FirebaseService firebaseService;
    private GetChapterRequestLogManager getChapterRequestLogManager;

    @Autowired
    public GetChapterByBookNameAndChapterNumberActivity(
        ChapterManager chapterManager,
        FirebaseService firebaseService,
        GetChapterRequestLogManager getChapterRequestLogManager
    ) {
        this.chapterManager = chapterManager;
        this.firebaseService = firebaseService;
        this.getChapterRequestLogManager = getChapterRequestLogManager;

    }

    @PostMapping(GET_CHAPTER_BY_BOOK_NAME_AND_CHAPTER_NUMBER)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetChapterByBookNameAndChapterNumberResponse getChapterByBookNameAndChapterNumber(
        @RequestBody GetChapterByBookNameAndChapterNumberRequest getChapterByBookNameAndChapterNumberRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        Long startTime = Instant.now().getEpochSecond();
        Long startTimeMilli = Instant.now().toEpochMilli();
        String createdBy = firebaseService.getEmail(authToken);
        getChapterRequestLogManager.createGetChapterRequestLog(
            getChapterByBookNameAndChapterNumberRequest.getBookName(),
            Long.parseLong(getChapterByBookNameAndChapterNumberRequest.getChapterNumber()),
            createdBy
        );
        Chapter chapter = chapterManager.getChapterByBookNameAndChapterNumber(
            getChapterByBookNameAndChapterNumberRequest.getBookName(),
            Long.parseLong(getChapterByBookNameAndChapterNumberRequest.getChapterNumber()),
            createdBy
        );
        GetChapterByBookNameAndChapterNumberResponse getChapterByBookNameAndChapterNumberResponse =
            GetChapterByBookNameAndChapterNumberResponse.builder()
                .chapter(chapter)
                .build();
        Long endTime = Instant.now().getEpochSecond();
        Long timeSeconds = endTime - startTime;
        Long endTimeMilli = Instant.now().toEpochMilli();
        Long timeMilli = endTimeMilli - startTimeMilli;
        log.info("GetChapterByBookNameAndChapterName Runtime in seconds: " + timeSeconds);
        log.info("GetChapterByBookNameAndChapterName Runtime in milliseconds: " + timeMilli);
        return getChapterByBookNameAndChapterNumberResponse;
    }
}
