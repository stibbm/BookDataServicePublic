package book.data.service.activity.chapter;


import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_CHAPTERS_BY_BOOK_NAME_PAGED;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.RequestLogManager;
import book.data.service.manager.ResponseLogManager;
import book.data.service.manager.chapter.ChapterManager;
import book.data.service.message.chapter.GetChaptersByBookNamePagedRequest;
import book.data.service.message.chapter.GetChaptersByBookNamePagedResponse;
import book.data.service.sqlmodel.chapter.Chapter;
import java.time.Instant;
import java.util.List;
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
public class GetChaptersByBookNamePagedActivity {
    private ChapterManager chapterManager;
    private RequestLogManager requestLogManager;
    private ResponseLogManager responseLogManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetChaptersByBookNamePagedActivity(
        ChapterManager chapterManager,
        RequestLogManager requestLogManager,
        ResponseLogManager responseLogManager,
        FirebaseService firebaseService
    ) {
        this.chapterManager = chapterManager;
        this.requestLogManager = requestLogManager;
        this.responseLogManager = responseLogManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_CHAPTERS_BY_BOOK_NAME_PAGED)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetChaptersByBookNamePagedResponse getChaptersByBookNamePaged(
        @RequestBody GetChaptersByBookNamePagedRequest getChaptersByBookNamePagedRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        Long startTime = Instant.now().getEpochSecond();
        String createdBy = firebaseService.getEmail(authToken);
        /*RequestLog requestLog = requestLogManager.createRequestLog(
            GET_CHAPTERS_BY_BOOK_NAME_PAGED,
            GSON.toJson(getChaptersByBookNamePagedRequest)
        );*/
        List<Chapter> chapterList = chapterManager.getChaptersByBookNamePaged(
            getChaptersByBookNamePagedRequest.getBookName(),
            Integer.parseInt(getChaptersByBookNamePagedRequest.getPageNumber()),
            Integer.parseInt(getChaptersByBookNamePagedRequest.getPageSize()),
            createdBy
        );
        GetChaptersByBookNamePagedResponse getChaptersByBookNamePagedResponse =
            GetChaptersByBookNamePagedResponse.builder()
                .chapterList(chapterList)
                .pageNumber(Integer.parseInt(getChaptersByBookNamePagedRequest.getPageNumber()))
                .pageSize(Integer.parseInt(getChaptersByBookNamePagedRequest.getPageSize()))
                .build();
        /*responseLogManager.createResponseLog(
            GET_CHAPTERS_BY_BOOK_NAME_PAGED,
            GSON.toJson(getChaptersByBookNamePagedResponse),
            requestLog.getRequestUuid()
        );*/
        Long endTime = Instant.now().getEpochSecond();
        Long timeSeconds = endTime - startTime;
        log.info("GetChaptersByBookNamePaged Runtime in seconds: " + timeSeconds);
        return getChaptersByBookNamePagedResponse;
    }
}
