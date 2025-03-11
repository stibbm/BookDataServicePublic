package book.data.service.manager.stripe.checkout;

import book.data.service.clientwrapper.StripeClientWrapper;
import book.data.service.dao.checkout.CheckoutSessionDAO;
import book.data.service.model.StripeCheckoutSession;
import book.data.service.service.time.TimeService;
import book.data.service.sqlmodel.checkoutsession.CheckoutSession;
import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckoutSessionManager {
    private CheckoutSessionDAO checkoutSessionDAO;
    private TimeService timeService;
    private StripeClientWrapper stripeClientWrapper;

    @Autowired
    public CheckoutSessionManager(
            CheckoutSessionDAO checkoutSessionDAO,
            TimeService timeService,
            StripeClientWrapper stripeClientWrapper
    ) {
        this.checkoutSessionDAO = checkoutSessionDAO;
        this.timeService = timeService;
        this.stripeClientWrapper = stripeClientWrapper;
    }

    public StripeCheckoutSession getStripeCheckoutSessionBySessionId(
            String sessionId
    ) throws StripeException {
        return stripeClientWrapper.getSessionBySessionId(sessionId);
    }

    public CheckoutSession getCheckoutSessionBySessionId(
            String sessionId,
            String createdBy
    ) {
        return checkoutSessionDAO.getCheckoutSessionBySessionId(
                sessionId, createdBy
        );
    }

    public CheckoutSession createCheckoutSession(
            String stripeSessionId,
            String priceId,
            String createdBy
    ) {
        Long createdEpochMilli = timeService.getCurrentLocalDateTimeLong();
        return checkoutSessionDAO.createCheckoutSession(
                stripeSessionId,
                priceId,
                createdBy,
                createdEpochMilli
        );
    }

}
