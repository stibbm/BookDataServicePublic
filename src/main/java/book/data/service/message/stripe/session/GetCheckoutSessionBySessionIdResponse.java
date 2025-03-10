package book.data.service.message.stripe.session;

import book.data.service.sqlmodel.checkoutsession.CheckoutSession;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCheckoutSessionBySessionIdResponse implements Serializable {
    private CheckoutSession checkoutSession;
}
