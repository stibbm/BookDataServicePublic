package book.data.service.activity.video;

import static book.data.service.constants.Routes.GET_VIDEOS_BY_BOOK_NAME_AND_CHAPTER_NUMBER;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.VideoManager;
import book.data.service.message.video.GetVideosByBookNameAndChapterNumberRequest;
import book.data.service.message.video.GetVideosByBookNameAndChapterNumberResponse;
import book.data.service.sqlmodel.video.Video;
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
public class GetVideosByBookNameAndChapterNumberActivity {

    private VideoManager videoManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetVideosByBookNameAndChapterNumberActivity(VideoManager videoManager, FirebaseService firebaseService) {
        this.videoManager = videoManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_VIDEOS_BY_BOOK_NAME_AND_CHAPTER_NUMBER)
    @CrossOrigin("*")
    public @ResponseBody GetVideosByBookNameAndChapterNumberResponse getVideosByBookNameAndChapterNumber(
        @RequestBody GetVideosByBookNameAndChapterNumberRequest getVideosByBookNameAndChapterNumberRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        log.info("getVideos: " + getVideosByBookNameAndChapterNumberRequest);
        getVideosByBookNameAndChapterNumberRequest.validate();
        String email = firebaseService.getEmail(authToken);
        List<Video> videoList = videoManager.getVideosByBookNameAndChapterNumber(
            getVideosByBookNameAndChapterNumberRequest.getBookName(),
            Long.parseLong(getVideosByBookNameAndChapterNumberRequest.getChapterNumber()),
            email
        );
        log.info("videoList: " + videoList);
        GetVideosByBookNameAndChapterNumberResponse getVideosByBookNameAndChapterNumberResponse =
            GetVideosByBookNameAndChapterNumberResponse.builder().videoList(videoList).build();
        return getVideosByBookNameAndChapterNumberResponse;
    }
}
