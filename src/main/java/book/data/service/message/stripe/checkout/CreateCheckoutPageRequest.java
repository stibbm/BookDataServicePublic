package book.data.service.message.stripe.checkout;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCheckoutPageRequest implements Serializable {
    private String itemName;
    private String baseUrl;
}
