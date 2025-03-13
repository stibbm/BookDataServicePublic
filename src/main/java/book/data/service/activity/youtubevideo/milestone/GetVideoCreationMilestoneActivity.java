package book.data.service.activity.youtubevideo.milestone;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_VIDEO_CREATION_MILESTONE;

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
public class GetVideoCreationMilestoneActivity {
    private VideoCreationMilestoneManager videoCreationMilestoneManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetVideoCreationMilestoneActivity(VideoCreationMilestoneManager videoCreationMilestoneManager,
        FirebaseService firebaseService) {
        this.videoCreationMilestoneManager = videoCreationMilestoneManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_VIDEO_CREATION_MILESTONE)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody book.data.service.youtube.milestone.GetVideoCreationMilestoneResponse getVideoCreationMilestoneResponse(
        @RequestBody book.data.service.youtube.milestone.GetVideoCreationMilestoneRequest request,
        @RequestHeader("Authorization") String authToken
    ) {
        log.info("getVideoCreationMilestone::: " + request);
        String email = firebaseService.getEmail(authToken);
        PaymentForTranslatedYoutubeBook paymentForTranslatedYoutubeBook = videoCreationMilestoneManager.getMilestone(
            Long.parseLong(request.getBookNumber()),
            Long.parseLong(request.getStartChapterNumber()),
            Long.parseLong(request.getEndChapterNumber())
        );
        //log.info("paymentForTranslatedYoutubeBook: " + paymentForTranslatedYoutubeBook);
        book.data.service.youtube.milestone.GetVideoCreationMilestoneResponse getVideoCreationMilestoneResponse =
            book.data.service.youtube.milestone.GetVideoCreationMilestoneResponse.builder()
                .videoCreationMilestone(paymentForTranslatedYoutubeBook.getVideoCreationMilestone())
                .bookNumber(Long.parseLong(request.getBookNumber()))
                .startChapterNumber(Long.parseLong(request.getStartChapterNumber()))
                .endChapterNumber(Long.parseLong(request.getEndChapterNumber()))
                .build();
        return getVideoCreationMilestoneResponse;
    }

}
