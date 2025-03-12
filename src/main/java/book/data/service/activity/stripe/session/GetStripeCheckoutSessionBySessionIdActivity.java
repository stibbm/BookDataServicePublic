package book.data.service.activity.stripe.session;


import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_STRIPE_CHECKOUT_SESSION_BY_SESSION_ID;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.stripe.checkout.CheckoutSessionManager;
import book.data.service.message.stripe.checkout.GetStripeCheckoutSessionBySessionIdRequest;
import book.data.service.message.stripe.checkout.GetStripeCheckoutSessionBySessionIdResponse;
import book.data.service.model.StripeCheckoutSession;
import com.stripe.exception.StripeException;
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
public class GetStripeCheckoutSessionBySessionIdActivity {

    public CheckoutSessionManager checkoutSessionManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetStripeCheckoutSessionBySessionIdActivity(
        CheckoutSessionManager checkoutSessionManager, FirebaseService firebaseService
    ) {
        this.checkoutSessionManager = checkoutSessionManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_STRIPE_CHECKOUT_SESSION_BY_SESSION_ID)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetStripeCheckoutSessionBySessionIdResponse getStripeCheckoutSessionBySessionId(
        @RequestBody GetStripeCheckoutSessionBySessionIdRequest getStripeCheckoutSessionBySessionIdRequest,
        @RequestHeader("Authorization") String authToken
    ) throws StripeException {
        log.info("getStripeCheckoutSessionBySessionId: " + getStripeCheckoutSessionBySessionIdRequest);
        StripeCheckoutSession stripeCheckoutSession = checkoutSessionManager.getStripeCheckoutSessionBySessionId(
            getStripeCheckoutSessionBySessionIdRequest.getSessionId());
        GetStripeCheckoutSessionBySessionIdResponse getStripeCheckoutSessionBySessionIdResponse =
            GetStripeCheckoutSessionBySessionIdResponse.builder()
                .stripeCheckoutSession(stripeCheckoutSession)
                .build();
        return getStripeCheckoutSessionBySessionIdResponse;
    }
}
