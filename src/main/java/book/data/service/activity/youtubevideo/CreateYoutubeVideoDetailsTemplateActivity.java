package book.data.service.activity.youtubevideo;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.CREATE_YOUTUBE_VIDEO_DETAILS_TEMPLATE;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.youtubevideo.YoutubeVideoDetailsTemplateManager;
import book.data.service.sqlmodel.youtubevideo.YoutubeVideoDetailsTemplate;
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
public class CreateYoutubeVideoDetailsTemplateActivity {

    private YoutubeVideoDetailsTemplateManager youtubeVideoDetailsTemplateManager;
    private FirebaseService firebaseService;

    @Autowired
    public CreateYoutubeVideoDetailsTemplateActivity(
        YoutubeVideoDetailsTemplateManager youtubeVideoDetailsTemplateManager,
        FirebaseService firebaseService
    ) {
        this.youtubeVideoDetailsTemplateManager = youtubeVideoDetailsTemplateManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(CREATE_YOUTUBE_VIDEO_DETAILS_TEMPLATE)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody book.data.service.youtube.CreateYoutubeVideoDetailsTemplateResponse createYoutubeVideoDetailsTemplate(
        @RequestBody book.data.service.youtube.CreateYoutubeVideoDetailsTemplateRequest createYoutubeVideoDetailsTemplateRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        String email = firebaseService.getEmail(authToken);
        YoutubeVideoDetailsTemplate youtubeVideoDetailsTemplate = youtubeVideoDetailsTemplateManager.createYoutubeVideoDetailsTemplate(
            Long.parseLong(createYoutubeVideoDetailsTemplateRequest.getBookNumber()),
            createYoutubeVideoDetailsTemplateRequest.getTitleTemplate(),
            createYoutubeVideoDetailsTemplateRequest.getDescriptionTemplate(),
            createYoutubeVideoDetailsTemplateRequest.getVideoTagList()
        );
        if (youtubeVideoDetailsTemplate == null) {
            throw new RuntimeException("failed to create YoutubeVideoDetailsTemplate!");
        }
        book.data.service.youtube.CreateYoutubeVideoDetailsTemplateResponse createYoutubeVideoDetailsTemplateResponse =
            book.data.service.youtube.CreateYoutubeVideoDetailsTemplateResponse.builder()
                .youtubeVideoDetailsTemplate(youtubeVideoDetailsTemplate).build();
        return createYoutubeVideoDetailsTemplateResponse;
    }
}
