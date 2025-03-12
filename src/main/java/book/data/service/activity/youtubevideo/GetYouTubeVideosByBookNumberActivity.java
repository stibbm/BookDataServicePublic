package book.data.service.activity.youtubevideo;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_YOUTUBE_VIDEOS_BY_BOOK_NUMBER;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.youtubevideo.YouTubeVideoManager;
import book.data.service.message.youtube.GetYouTubeVideosByBookNumberRequest;
import book.data.service.message.youtube.GetYouTubeVideosByBookNumberResponse;
import book.data.service.sqlmodel.youtubevideo.YouTubeVideo;
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
public class GetYouTubeVideosByBookNumberActivity {

    private YouTubeVideoManager youTubeVideoManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetYouTubeVideosByBookNumberActivity(YouTubeVideoManager youTubeVideoManager,
        FirebaseService firebaseService) {
        this.youTubeVideoManager = youTubeVideoManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_YOUTUBE_VIDEOS_BY_BOOK_NUMBER)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetYouTubeVideosByBookNumberResponse getYouTubeVideosByBookNumber(
        @RequestBody GetYouTubeVideosByBookNumberRequest getYouTubeVideosByBookNumberRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        String email = firebaseService.getEmail(authToken);
        List<YouTubeVideo> youTubeVideoList = youTubeVideoManager.getYouTubeVideosByBookNumber(
            Long.parseLong(getYouTubeVideosByBookNumberRequest.getBookNumber())
        );
        if (youTubeVideoList == null) {
            throw new RuntimeException("failed to retrieve videos list by book number");
        }
        return GetYouTubeVideosByBookNumberResponse.builder().youTubeVideoList(youTubeVideoList)
            .build();
    }
}
