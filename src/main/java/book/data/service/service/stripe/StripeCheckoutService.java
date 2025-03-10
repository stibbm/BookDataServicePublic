package book.data.service.service.stripe;


import book.data.service.clientwrapper.StripeClientWrapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.LineItem;
import com.stripe.model.LineItemCollection;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.Mode;
import com.stripe.param.checkout.SessionCreateParams.PaymentMethodType;
import com.stripe.model.checkout.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StripeCheckoutService {

    private StripeClientWrapper stripeClientWrapper;
    private CheckoutSessionDAO checkoutSessionDAO;
    private TimeService timeService;
    private AccountDAO accountDAO;

    @Autowired
    public StripeCheckoutService(StripeClientWrapper stripeClientWrapper,
        CheckoutSessionDAO checkoutSessionDAO,
        TimeService timeService,
        AccountDAO accountDAO
    ) {
        this.stripeClientWrapper = stripeClientWrapper;
        this.checkoutSessionDAO = checkoutSessionDAO;
        this.timeService = timeService;
        this.accountDAO = accountDAO;
    }

    public StripeCheckoutSession getCheckoutSessionBySessionId(String sessionId)
        throws StripeException {
        return stripeClientWrapper.getSessionBySessionId(sessionId);
    }

    public String createSession(String itemName, String baseUrl, String createdBy) throws StripeException {
        Stripe.apiKey = STRIPE_SECRET_API_KEY;
        String priceId = "";
        if (itemName.equalsIgnoreCase(ONE_K_TOKENS_ITEM_NAME)) {
            priceId = ONE_K_TOKENS_PRICE_ID;
        } else if (itemName.equalsIgnoreCase(FIVE_K_TOKENS_ITEM_NAME)) {
            priceId = FIVE_K_TOKENS_PRICE_ID;
        } else if (itemName.equalsIgnoreCase(TEN_K_TOKENS_ITEM_NAME)) {
            priceId = TEN_K_TOKENS_PRICE_ID;
        } else if (itemName.equalsIgnoreCase(TWENTY_FIVE_K_TOKENS_ITEM_NAME)) {
            priceId = TWENTY_FIVE_K_TOKENS_PRICE_ID;
        } else {
            throw new IllegalArgumentException("Item Name not recognized");
        }
        Account account = accountDAO.getAccountByEmailAddress(createdBy);
        String customerId = account.getStripeCustomerId();
        SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
            .addPaymentMethodType(PaymentMethodType.CARD)
            .setMode(Mode.PAYMENT)
            .setCustomer(customerId)
            .setSuccessUrl(
                baseUrl + "/checkout/success?session_id={CHECKOUT_SESSION_ID}")
            .setCancelUrl(baseUrl + "/checkout/failure")
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPrice(priceId)
                    .build()
            )
            .build();
        Session session = Session.create(sessionCreateParams);
        Long createdEpochMilli = timeService.getCurrentLocalDateTimeLong();
        checkoutSessionDAO.createCheckoutSession(
            session.getId(),
            priceId,
            createdBy,
            createdEpochMilli
        );
        return session.getUrl();
    }

}
