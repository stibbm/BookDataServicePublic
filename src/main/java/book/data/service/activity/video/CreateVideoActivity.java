package book.data.service.activity.video;

import static book.data.service.constants.Constants.S3_VIDEO_BUCKET;
import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.CREATE_VIDEO;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.VideoManager;
import book.data.service.message.video.CreateVideoRequest;
import book.data.service.message.video.CreateVideoResponse;
import book.data.service.sqlmodel.video.Video;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
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
public class CreateVideoActivity {
    private VideoManager videoManager;
    private FirebaseService firebaseService;

    @Autowired
    private CreateVideoActivity(VideoManager videoManager, FirebaseService firebaseService) {
        this.videoManager = videoManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(CREATE_VIDEO)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody CreateVideoResponse createVideo(
        @RequestBody CreateVideoRequest createVideoRequest,
        @RequestHeader("Authorization") String authToken
    ) throws MalformedURLException, URISyntaxException {
        createVideoRequest.validate();
        String email = firebaseService.getEmail(authToken);
        Video video = videoManager.createVideo(
            createVideoRequest.getBookName(),
            Long.parseLong(createVideoRequest.getChapterNumber()),
            Long.parseLong(createVideoRequest.getVideoNumber()),
            createVideoRequest.getVideoBytes(),
            createVideoRequest.getFileType(),
            S3_VIDEO_BUCKET,
            email
        );
        return CreateVideoResponse.builder().video(video).build();
    }
}
