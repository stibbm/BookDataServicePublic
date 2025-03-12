package book.data.service.activity.chapter;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_CHAPTER_HEADERS_BY_BOOK_NUMBER;

import book.data.service.message.chapter.chapterheader.GetChapterHeadersByBookNumberRequest;
import book.data.service.message.chapter.chapterheader.GetChapterHeadersByBookNumberResponse;
import book.data.service.model.ChapterHeader;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.chapter.ChapterHeaderManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class GetChapterHeadersByBookNumberActivity {

    private ChapterHeaderManager chapterHeaderManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetChapterHeadersByBookNumberActivity(
        ChapterHeaderManager chapterHeaderManager,
        FirebaseService firebaseService
    ) {
        this.chapterHeaderManager = chapterHeaderManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_CHAPTER_HEADERS_BY_BOOK_NUMBER)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetChapterHeadersByBookNumberResponse getChapterHeadersByBookNumber(
        @RequestBody GetChapterHeadersByBookNumberRequest getChapterHeadersByBookNumberRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        log.info("GetChapterHeadersByBookNumber");
        String email = firebaseService.getEmail(authToken);
        List<ChapterHeader> chapterHeaderList = chapterHeaderManager.getChapterHeadersByBookNumber(
            Long.parseLong(getChapterHeadersByBookNumberRequest.getBookNumber()),
            email
        );
        GetChapterHeadersByBookNumberResponse getChapterHeadersByBookNumberResponse =
            GetChapterHeadersByBookNumberResponse.builder().chapterHeaderList(chapterHeaderList)
                .build();
        return getChapterHeadersByBookNumberResponse;
    }
}
