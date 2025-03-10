package book.data.service.sqlmodel.checkoutsession;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "CheckoutSession")
@Table(name = "checkoutSession")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CheckoutSession implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long checkoutSessionNumber;

    @Column(nullable = false)
    private String stripeSessionId;

    @Column(nullable = false)
    private String priceId;

    private String createdBy;

    private Long createdEpochMilli;

    public static CheckoutSession createCheckoutSessionWithoutId(
        String stripeSessionId,
        String priceId,
        String createdBy,
        Long createdEpochMilli
    ) {
        CheckoutSession checkoutSession = new CheckoutSession();
        checkoutSession.setStripeSessionId(stripeSessionId);
        checkoutSession.setPriceId(priceId);
        checkoutSession.setCreatedBy(createdBy);
        checkoutSession.setCreatedEpochMilli(createdEpochMilli);
        return checkoutSession;
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkoutSessionNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) {
            return true;
        }
        if (o==null || this.getClass() != o.getClass()) {
            return false;
        }
        CheckoutSession checkoutSession = (CheckoutSession) o;
        return Objects.equals(checkoutSessionNumber, checkoutSession.getCheckoutSessionNumber())
            && Objects.equals(priceId, checkoutSession.getPriceId())
            && Objects.equals(stripeSessionId, checkoutSession.getStripeSessionId())
            && Objects.equals(createdBy, checkoutSession.getCreatedBy())
            && Objects.equals(createdEpochMilli, checkoutSession.getCreatedEpochMilli());
    }

}
