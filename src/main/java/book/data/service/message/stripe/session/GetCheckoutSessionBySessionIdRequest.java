package book.data.service.message.stripe.session;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCheckoutSessionBySessionIdRequest implements Serializable {
    private String sessionId;
}
