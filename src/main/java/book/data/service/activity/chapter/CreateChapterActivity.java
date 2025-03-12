package book.data.service.activity.chapter;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.CREATE_CHAPTER;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.chapter.ChapterManager;
import book.data.service.message.chapter.CreateChapterRequest;
import book.data.service.message.chapter.CreateChapterResponse;
import book.data.service.sqlmodel.chapter.Chapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class CreateChapterActivity {
    private ChapterManager chapterManager;
    private FirebaseService firebaseService;

    @Autowired
    public CreateChapterActivity(
        ChapterManager chapterManager,
        FirebaseService firebaseService
    ) {
        this.chapterManager = chapterManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(CREATE_CHAPTER)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody CreateChapterResponse createChapter(
        @RequestBody CreateChapterRequest createChapterRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        log.info("Create Chapter: " + createChapterRequest.toString());
        String createdBy = firebaseService.getEmail(authToken);
        Chapter chapter = chapterManager.createChapter(
            createChapterRequest.getBookName(),
            Long.parseLong(createChapterRequest.getChapterNumber()),
            createChapterRequest.getChapterName(),
            Long.parseLong(createChapterRequest.getChapterViews()),
            createdBy
        );
        CreateChapterResponse createChapterResponse =
            CreateChapterResponse.builder().chapter(chapter).build();
        return createChapterResponse;
    }
}
