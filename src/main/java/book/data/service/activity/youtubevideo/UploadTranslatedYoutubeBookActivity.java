package book.data.service.activity.youtubevideo;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.uploadtranslatedyoutubebook.UploadTranslatedYoutubeBookManager;
import book.data.service.message.youtubebook.UploadTranslatedYoutubeBookRequest;
import book.data.service.message.youtubebook.UploadTranslatedYoutubeBookResponse;
import book.data.service.model.YouTubeVideo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.UPLOAD_TRANSLATED_YOUTUBE_BOOK;

@Slf4j
@RestController
public class UploadTranslatedYoutubeBookActivity {
    private UploadTranslatedYoutubeBookManager uploadTranslatedYoutubeBookManager;
    private FirebaseService firebaseService;

    @Autowired
    public UploadTranslatedYoutubeBookActivity(UploadTranslatedYoutubeBookManager uploadTranslatedYoutubeBookManager,
                                               FirebaseService firebaseService) {
        this.uploadTranslatedYoutubeBookManager = uploadTranslatedYoutubeBookManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(UPLOAD_TRANSLATED_YOUTUBE_BOOK)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody UploadTranslatedYoutubeBookResponse uploadTranslatedYoutubeBook(
            @RequestBody UploadTranslatedYoutubeBookRequest uploadTranslatedYoutubeBookRequest,
            @RequestHeader("Authorization") String authToken
    ) throws IOException {
        log.info("uploadTranslatedYoutubeBook");
        log.info("bookNumber: " + uploadTranslatedYoutubeBookRequest.getBookNumber());
        log.info("startChapterNumber: " + uploadTranslatedYoutubeBookRequest.getStartChapterNumber());
        log.info("endChapterNumber: " + uploadTranslatedYoutubeBookRequest.getEndChapterNumber());
        // log.info(uploadTranslatedYoutubeBookRequest.toString());
        //String email = firebaseService.getEmail(authToken);
        //firebaseService.
        String email = "manhwaandwebnovelrecaps@gmail.com";
        YouTubeVideo youtubeVideo = uploadTranslatedYoutubeBookManager.uploadTranslatedYoutubeBook(
                uploadTranslatedYoutubeBookRequest.getVideoData(),
                Long.parseLong(uploadTranslatedYoutubeBookRequest.getBookNumber()),
                Long.parseLong(uploadTranslatedYoutubeBookRequest.getStartChapterNumber()),
                Long.parseLong(uploadTranslatedYoutubeBookRequest.getEndChapterNumber()),
                email
        );
        log.info("YoutubeVideo : " + youtubeVideo);
        if (youtubeVideo == null) {
            throw new RuntimeException("failed to upload youtube video for: " + uploadTranslatedYoutubeBookRequest.toString());
        }
        UploadTranslatedYoutubeBookResponse uploadTranslatedYoutubeBookResponse =
                UploadTranslatedYoutubeBookResponse.builder().youTubeVideo(youtubeVideo).build();
        return uploadTranslatedYoutubeBookResponse;
    }
}
