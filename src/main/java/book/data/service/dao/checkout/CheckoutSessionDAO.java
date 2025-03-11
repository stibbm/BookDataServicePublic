package book.data.service.dao.checkout;

import lombok.extern.slf4j.Slf4j;
import book.data.service.repository.CheckoutSessionRepository;
import book.data.service.sqlmodel.checkoutsession.CheckoutSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CheckoutSessionDAO {
    private CheckoutSessionRepository checkoutSessionRepository;

    @Autowired
    public CheckoutSessionDAO(CheckoutSessionRepository checkoutSessionRepository) {
        this.checkoutSessionRepository = checkoutSessionRepository;
    }

    public CheckoutSession getCheckoutSessionBySessionId(
        String sessionId,
        String createdBy
    ) {
        return checkoutSessionRepository.findCheckoutSessionBySessionId(sessionId, createdBy);
    }

    public CheckoutSession createCheckoutSession(
        String stripeSessionId,
        String priceId,
        String createdBy,
        Long createdEpochMilli
    ) {
        CheckoutSession checkoutSession = CheckoutSession.createCheckoutSessionWithoutId(
            stripeSessionId,
            priceId,
            createdBy,
            createdEpochMilli
        );
        checkoutSessionRepository.saveCheckoutSession(checkoutSession);
        checkoutSession = checkoutSessionRepository.findCheckoutSessionBySessionId(stripeSessionId, createdBy);
        return checkoutSession;
    }
}
