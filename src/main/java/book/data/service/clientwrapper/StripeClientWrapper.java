package book.data.service.clientwrapper;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StripeClientWrapper {
    public Customer createCustomer(
            String emailAddress,
            String fullName
    ) throws StripeException {
        Stripe.apiKey = STRIPE_SECRET_API_KEY;
        log.info("create customer");
        log.info(emailAddress);
        log.info(fullName);
        CustomerCreateParams customerCreateParams = CustomerCreateParams
                .builder()
                .setEmail(emailAddress)
                .setName(fullName)
                .build();
        Customer customer = Customer.create(customerCreateParams);
        log.info(customer.toString());
        return customer;
    }

    public Customer getCustomer(String customerId) throws StripeException {
        Stripe.apiKey = STRIPE_SECRET_API_KEY;
        return Customer.retrieve(customerId);
    }

    public Price getPrice(String priceId) throws StripeException {
        Stripe.apiKey = STRIPE_SECRET_API_KEY;
        Price price = Price.retrieve(priceId);
        return price;
    }

    public Product getProduct(String productId) throws StripeException {
        Stripe.apiKey = STRIPE_SECRET_API_KEY;
        Product product = Product.retrieve(productId);
        return product;
    }

    public StripeCheckoutSession getSessionBySessionId(String sessionId) throws StripeException {
        Stripe.apiKey = STRIPE_SECRET_API_KEY;
        log.info("session: ");
        log.info("sessionID: " + sessionId);
        Session session = Session.retrieve(sessionId);
        log.info(session.toString());
        //String productName = session.getLineItems().getData().get(0).getProductObject().getName();
        String productName = null;
        String paymentStatus = session.getPaymentStatus();
        String customerId = session.getCustomer();
        String sessionStatus = session.getStatus();
        Long totalAmount = session.getAmountTotal();
        StripeCheckoutSession stripeCheckoutSession = StripeCheckoutSession.builder()
                .sessionId(sessionId)
                .productName(productName)
                .paymentStatus(paymentStatus)
                .customerId(customerId)
                .sessionStatus(sessionStatus)
                .totalAmount(totalAmount)
                .build();
        return stripeCheckoutSession;
    }
}
