package book.data.service.activity.youtubevideo;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_YOUTUBE_VIDEO_BY_YOUTUBE_VIDEO_ID;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.youtubevideo.YouTubeVideoManager;
import book.data.service.message.youtube.GetYouTubeVideoByYouTubeVideoIdRequest;
import book.data.service.message.youtube.GetYouTubeVideoByYouTubeVideoIdResponse;
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
public class GetYouTubeVideoByYouTubeVideoIdActivity {
    private YouTubeVideoManager youTubeVideoManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetYouTubeVideoByYouTubeVideoIdActivity(
        YouTubeVideoManager youTubeVideoManager,
        FirebaseService firebaseService
    ) {
        this.youTubeVideoManager = youTubeVideoManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_YOUTUBE_VIDEO_BY_YOUTUBE_VIDEO_ID)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetYouTubeVideoByYouTubeVideoIdResponse getYouTubeVideoByYouTubeVideoId(
        @RequestBody GetYouTubeVideoByYouTubeVideoIdRequest getYouTubeVideoByYouTubeVideoIdRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        String email = firebaseService.getEmail(authToken);
        YouTubeVideo youTubeVideo = youTubeVideoManager.getYouTubeVideoByYouTubeVideoId(
            getYouTubeVideoByYouTubeVideoIdRequest.getYouTubeVideoId()
        );
        if (youTubeVideo == null) {
            throw new RuntimeException("failed to retrieve the specified youTubeVideo by id: " +
                getYouTubeVideoByYouTubeVideoIdRequest.getYouTubeVideoId());
        }
        return GetYouTubeVideoByYouTubeVideoIdResponse.builder()
            .youTubeVideo(youTubeVideo)
            .build();
    }
}
