package book.data.service.activity.youtubevideo;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.CREATE_YOUTUBE_VIDEO;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.youtubevideo.YouTubeVideoManager;
import book.data.service.sqlmodel.youtubevideo.YouTubeVideo;
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
public class CreateYouTubeVideoActivity {
    private YouTubeVideoManager youTubeVideoManager;
    private FirebaseService firebaseService;

    @Autowired
    public CreateYouTubeVideoActivity(YouTubeVideoManager youTubeVideoManager,
        FirebaseService firebaseService) {
        this.youTubeVideoManager = youTubeVideoManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(CREATE_YOUTUBE_VIDEO)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody book.data.service.message.youtube.CreateYouTubeVideoResponse createYouTubeVideo(
        @RequestBody book.data.service.youtube.CreateYouTubeVideoRequest createYouTubeVideoRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        String email = firebaseService.getEmail(authToken);
        YouTubeVideo youTubeVideo = youTubeVideoManager.createYouTubeVideo(
            createYouTubeVideoRequest.getYoutubeVideoId(),
            Long.parseLong(createYouTubeVideoRequest.getBookNumber()),
            Long.parseLong(createYouTubeVideoRequest.getStartChapterNumber()),
            Long.parseLong(createYouTubeVideoRequest.getEndChapterNumber()),
            email
        );
        if (youTubeVideo==null) {
            throw new RuntimeException("Failed to successfully create youtube video");
        }
        return book.data.service.message.youtube.CreateYouTubeVideoResponse.builder().youTubeVideo(youTubeVideo).build();
    }
}
