package book.data.service.activity.stripe.webhook;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GSON;
import static book.data.service.constants.Routes.STRIPE_WEBHOOKS;

import book.data.service.webhookhandler.CheckoutSessionCompletedEventHandler;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.ApiResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HandleStripeWebhooks {
    private CheckoutSessionCompletedEventHandler checkoutSessionCompletedEventHandler;

    @Autowired
    public HandleStripeWebhooks(CheckoutSessionCompletedEventHandler checkoutSessionCompletedEventHandler) {
        this.checkoutSessionCompletedEventHandler = checkoutSessionCompletedEventHandler;
    }

    @PostMapping(STRIPE_WEBHOOKS)
    @CrossOrigin(ALL_ORIGINS)
    public void handleStripeWebhook(
        @RequestBody Object request,
        @RequestHeader("Stripe-Signature") String sigHeader
    ) throws StripeException {
        String requestString = GSON.toJson(request);
        log.info("request");
        log.info(GSON.toJson(request));
        Event event = ApiResource.GSON.fromJson(requestString, Event.class);
        EventDataObjectDeserializer eventDataObjectDeserializer = event.getDataObjectDeserializer();

        StripeObject stripeObject = eventDataObjectDeserializer.getObject().get();
        log.info("stripeObject");
        log.info(stripeObject.toString());

        log.info("event type: " + event.getType());

        switch(event.getType()) {
            case "payment_intent.succeeded":
                PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                log.info(paymentIntent.toString());
                break;
            case "checkout.session.completed":
                Session session = (Session) stripeObject;
                checkoutSessionCompletedEventHandler.handleEvent(session);
                break;
            default:
                log.info("unhandledEventType: " + event.getType());
        }
    }

}