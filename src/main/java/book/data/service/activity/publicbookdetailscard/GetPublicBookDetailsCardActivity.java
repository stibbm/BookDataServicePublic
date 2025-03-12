package book.data.service.activity.publicbookdetailscard;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_PUBLIC_BOOK_DETAILS_CARD_BY_BOOK_NUMBER;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.publicbookdetailscard.PublicBookDetailsCardManager;
import book.data.service.message.bookdetailscard.GetPublicBookDetailsCardByBookNumberRequest;
import book.data.service.message.bookdetailscard.GetPublicBookDetailsCardByBookNumberResponse;
import book.data.service.model.PublicBookDetailsCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GetPublicBookDetailsCardActivity {
    private PublicBookDetailsCardManager publicBookDetailsCardManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetPublicBookDetailsCardActivity(PublicBookDetailsCardManager publicBookDetailsCardManager,
        FirebaseService firebaseService) {
        this.publicBookDetailsCardManager = publicBookDetailsCardManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_PUBLIC_BOOK_DETAILS_CARD_BY_BOOK_NUMBER)
    @CrossOrigin(ALL_ORIGINS)
    public GetPublicBookDetailsCardByBookNumberResponse getPublicBookDetailsCard(
        @RequestBody GetPublicBookDetailsCardByBookNumberRequest getPublicBookDetailsCardByBookNumberRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        log.info("GetPublicBookDetailsCardByBookNumber called");
        String email = firebaseService.getEmail(authToken);
        PublicBookDetailsCard publicBookDetailsCard = publicBookDetailsCardManager.getPublicBookDetailsCardByBookNumber(
            Long.parseLong(getPublicBookDetailsCardByBookNumberRequest.getBookNumber()),
            email
        );
        GetPublicBookDetailsCardByBookNumberResponse getPublicBookDetailsCardByBookNumberResponse =
            GetPublicBookDetailsCardByBookNumberResponse.builder()
                .publicBookDetailsCard(publicBookDetailsCard).build();
        return getPublicBookDetailsCardByBookNumberResponse;
    }
}
