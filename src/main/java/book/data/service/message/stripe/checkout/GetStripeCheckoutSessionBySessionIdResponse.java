package book.data.service.message.stripe.checkout;

import book.data.service.model.StripeCheckoutSession;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetStripeCheckoutSessionBySessionIdResponse implements Serializable {
    private StripeCheckoutSession stripeCheckoutSession;
}
