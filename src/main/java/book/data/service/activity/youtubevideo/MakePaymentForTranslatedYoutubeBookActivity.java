package book.data.service.activity.youtubevideo;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.MAKE_TOKEN_PAYMENT;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.tokentransaction.TokenTransactionManager;
import book.data.service.message.tokentransaction.MakePaymentForTranslatedYoutubeBookRequest;
import book.data.service.message.tokentransaction.MakePaymentForTranslatedYoutubeBookResponse;
import book.data.service.sqlmodel.payment.PaymentForTranslatedYoutubeBook;
import book.data.service.sqlmodel.transaction.TokenTransaction;
import java.util.AbstractMap.SimpleEntry;
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
public class MakePaymentForTranslatedYoutubeBookActivity {

    private TokenTransactionManager tokenTransactionManager;
    private FirebaseService firebaseService;

    @Autowired
    public MakePaymentForTranslatedYoutubeBookActivity(
        TokenTransactionManager tokenTransactionManager, FirebaseService firebaseService) {
        this.tokenTransactionManager = tokenTransactionManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(MAKE_TOKEN_PAYMENT)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody MakePaymentForTranslatedYoutubeBookResponse act(
        @RequestBody MakePaymentForTranslatedYoutubeBookRequest request,
        @RequestHeader("Authorization") String authToken
    ) {
        log.info("MakePaymentForTranslatedYoutubeBookActivity called");
        log.info(request.toString());
        String email = firebaseService.getEmail(authToken);
        SimpleEntry<TokenTransaction, PaymentForTranslatedYoutubeBook> result =
            tokenTransactionManager.attemptTokenPaymentForTranslateYoutubeBook(
                request.getDedupeToken(),
                Long.parseLong(request.getBookNumber()),
                Long.parseLong(request.getStartChapter()),
                Long.parseLong(request.getEndChapter()),
                Long.parseLong(request.getTokenAmount()),
                email
            );
        log.info("MakePaymentForTranslatedYoutubeBookActivity result: " + result);
        MakePaymentForTranslatedYoutubeBookResponse response =
            MakePaymentForTranslatedYoutubeBookResponse.builder()
                .transaction(result.getKey())
                .payment(result.getValue())
                .build();
        return response;
    }
}
