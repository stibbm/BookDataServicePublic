package book.data.service.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StripeCheckoutSession implements Serializable {
    private String sessionId;
    private Long accountNumber;
    private String accountEmailAddress;
    private String productName;
    private String paymentStatus;
    private String customerId;
    private String sessionStatus;
    private Long totalAmount;
}
