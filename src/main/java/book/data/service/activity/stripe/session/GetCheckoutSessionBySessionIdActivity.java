package book.data.service.activity.stripe.session;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_CHECKOUT_SESSION_BY_SESSION_ID;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.stripe.checkout.CheckoutSessionManager;
import book.data.service.message.stripe.session.GetCheckoutSessionBySessionIdRequest;
import book.data.service.message.stripe.session.GetCheckoutSessionBySessionIdResponse;
import book.data.service.sqlmodel.checkoutsession.CheckoutSession;
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
public class GetCheckoutSessionBySessionIdActivity {

    private CheckoutSessionManager checkoutSessionManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetCheckoutSessionBySessionIdActivity(CheckoutSessionManager checkoutSessionManager,
        FirebaseService firebaseService) {
        this.checkoutSessionManager = checkoutSessionManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_CHECKOUT_SESSION_BY_SESSION_ID)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetCheckoutSessionBySessionIdResponse getSessionBySessionId(
        @RequestBody GetCheckoutSessionBySessionIdRequest getSessionByIdRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        log.info("getCheckoutSessionBySessionId: ");
        log.info(getSessionByIdRequest.toString());
        String createdBy = firebaseService.getEmail(authToken);
        CheckoutSession checkoutSession = checkoutSessionManager.getCheckoutSessionBySessionId(
            getSessionByIdRequest.getSessionId(), createdBy
        );
        return GetCheckoutSessionBySessionIdResponse.builder()
            .checkoutSession(checkoutSession).build();
    }
}
