package book.data.service.activity.stripe.checkout;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.CREATE_CHECKOUT_PAGE;

import book.data.service.firebase.FirebaseService;
import book.data.service.message.stripe.checkout.CreateCheckoutPageRequest;
import book.data.service.message.stripe.checkout.CreateCheckoutPageResponse;
import book.data.service.service.stripe.StripeCheckoutService;
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
public class CreateCheckoutPageActivity {

    private StripeCheckoutService stripeCheckoutService;
    private FirebaseService firebaseService;

    @Autowired
    public CreateCheckoutPageActivity(StripeCheckoutService stripeCheckoutService,
        FirebaseService firebaseService) {
        this.stripeCheckoutService = stripeCheckoutService;
        this.firebaseService = firebaseService;
    }

    @PostMapping(CREATE_CHECKOUT_PAGE)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody CreateCheckoutPageResponse createCheckoutPage(
        @RequestBody CreateCheckoutPageRequest createCheckoutPageRequest,
        @RequestHeader("Authorization") String authToken
    ) throws StripeException {
        String createdBy = firebaseService.getEmail(authToken);
        String sessionUrl = stripeCheckoutService.createSession(
            createCheckoutPageRequest.getItemName(), createCheckoutPageRequest.getBaseUrl(),
            createdBy);
        CreateCheckoutPageResponse createCheckoutPageResponse =
            CreateCheckoutPageResponse.builder()
                .checkoutPageUrl(sessionUrl)
                .build();
        return createCheckoutPageResponse;
    }
}
