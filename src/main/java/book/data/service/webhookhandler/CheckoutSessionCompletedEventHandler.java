package book.data.service.webhookhandler;

import book.data.service.clientwrapper.StripeClientWrapper;
import book.data.service.constants.Stripe;
import book.data.service.manager.account.AccountManager;
import book.data.service.manager.stripe.checkout.CheckoutSessionManager;
import book.data.service.manager.tokentransaction.TokenTransactionManager;
import book.data.service.model.StripeCheckoutSession;
import book.data.service.sqlmodel.account.Account;
import book.data.service.sqlmodel.checkoutsession.CheckoutSession;
import book.data.service.sqlmodel.transaction.TokenTransaction;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckoutSessionCompletedEventHandler {

    private CheckoutSessionManager checkoutSessionManager;
    private AccountManager accountManager;
    private TokenTransactionManager tokenTransactionManager;
    private StripeClientWrapper stripeClientWrapper;

    @Autowired
    public CheckoutSessionCompletedEventHandler(
        CheckoutSessionManager checkoutSessionManager,
        AccountManager accountManager,
        TokenTransactionManager tokenTransactionManager,
        StripeClientWrapper stripeClientWrapper
    ) {
        this.checkoutSessionManager = checkoutSessionManager;
        this.accountManager = accountManager;
        this.tokenTransactionManager = tokenTransactionManager;
        this.stripeClientWrapper = stripeClientWrapper;
    }

    public void handleEvent(Session session) throws StripeException {
        log.info("handleCheckoutCompletedEvent");
        log.info(session.toString());
        String sessionId = session.getId();
        StripeCheckoutSession stripeCheckoutSession = checkoutSessionManager.getStripeCheckoutSessionBySessionId(
            sessionId);
        String customerId = stripeCheckoutSession.getCustomerId();
        Account account = accountManager.getAccountByStripeCustomerId(customerId);
        String accountEmailAddress = account.getEmailAddress();
        CheckoutSession checkoutSession = checkoutSessionManager.getCheckoutSessionBySessionId(sessionId, accountEmailAddress);
        log.info("checkoutSession: " + checkoutSession);
        String priceId = checkoutSession.getPriceId();
        Price price = stripeClientWrapper.getPrice(priceId);
        String productId = price.getProduct();
        Product product = stripeClientWrapper.getProduct(productId);
        String productName = product.getName();
        Long tokenAmount = 0L;
        log.info("product: " + product);
        log.info("productName: " + product.getName());
        log.info("productName: " + productName);
        log.info("5k Tokens:   " +  Stripe.FIVE_K_TOKENS_PRODUCT_NAME);
        log.info("" + Stripe.FIVE_K_TOKENS_PRODUCT_NAME.equalsIgnoreCase(productName));
        if (Stripe.ONE_K_TOKENS_PRODUCT_NAME.equals(productName)) {
            tokenAmount=1000L;
        }
        else if (Stripe.FIVE_K_TOKENS_PRODUCT_NAME.equals(productName)) {
            tokenAmount=5000L;
        }
        else if(Stripe.TEN_K_TOKENS_PRODUCT_NAME.equals(productName)) {
            tokenAmount=10000L;
        }
        else if (Stripe.TWENTY_FIVE_K_TOKENS_PRODUCT_NAME.equals(productName)) {
            tokenAmount=25000L;
        }
        else {
            log.info("productName: " + productName);
            log.info("5k Tokens:   " +  Stripe.FIVE_K_TOKENS_ITEM_NAME);
            throw new IllegalArgumentException("Did not recognize a valid product name");
        }
        log.info("create token transaction with amount: " + tokenAmount);
        TokenTransaction tokenTransaction = tokenTransactionManager.createTokenTransaction(
            checkoutSession.getCheckoutSessionNumber(),
            tokenAmount,
            account.getEmailAddress()
        );
        log.info("created token transaction: " + tokenTransaction);
    }
}
