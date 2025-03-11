package book.data.service.manager.stripe.price;


import book.data.service.clientwrapper.StripeClientWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StripePriceManager {
    private StripeClientWrapper stripeClientWrapper;

    @Autowired
    public StripePriceManager(StripeClientWrapper stripeClientWrapper) {
        this.stripeClientWrapper = stripeClientWrapper;
    }


}
