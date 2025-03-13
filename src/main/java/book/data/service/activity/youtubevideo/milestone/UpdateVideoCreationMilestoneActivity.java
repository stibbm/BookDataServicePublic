package book.data.service.activity.youtubevideo.milestone;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.UPDATE_VIDEO_CREATION_MILESTONE;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.milestone.VideoCreationMilestoneManager;
import book.data.service.sqlmodel.payment.PaymentForTranslatedYoutubeBook;
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
public class UpdateVideoCreationMilestoneActivity {
    private VideoCreationMilestoneManager videoCreationMilestoneManager;
    private FirebaseService firebaseService;

    @Autowired
    public UpdateVideoCreationMilestoneActivity(
        VideoCreationMilestoneManager videoCreationMilestoneManager,
        FirebaseService firebaseService
    ) {
        this.videoCreationMilestoneManager = videoCreationMilestoneManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(UPDATE_VIDEO_CREATION_MILESTONE)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody book.data.service.youtube.milestone.UpdateVideoCreationMilestoneResponse act(
        @RequestBody book.data.service.youtube.milestone.UpdateVideoCreationMilestoneRequest updateVideoCreationMilestoneRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        log.info(UPDATE_VIDEO_CREATION_MILESTONE);
        log.info(updateVideoCreationMilestoneRequest.toString());
        PaymentForTranslatedYoutubeBook paymentForTranslatedYoutubeBook = this.videoCreationMilestoneManager.updateMilestone(
            updateVideoCreationMilestoneRequest.getVideoCreationMilestone(),
            Long.parseLong(updateVideoCreationMilestoneRequest.getBookNumber()),
            Long.parseLong(updateVideoCreationMilestoneRequest.getStartChapterNumber()),
            Long.parseLong(updateVideoCreationMilestoneRequest.getEndChapterNumber())
        );
        log.info("paymentForTranslatedYoutubeBook: " + paymentForTranslatedYoutubeBook);
        return book.data.service.youtube.milestone.UpdateVideoCreationMilestoneResponse.builder()
            .paymentForTranslatedYoutubeBook(paymentForTranslatedYoutubeBook)
            .build();
    }

}
