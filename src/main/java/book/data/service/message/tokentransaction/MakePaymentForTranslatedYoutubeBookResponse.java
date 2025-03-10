package book.data.service.message.tokentransaction;

import book.data.service.sqlmodel.payment.PaymentForTranslatedYoutubeBook;
import book.data.service.sqlmodel.transaction.TokenTransaction;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MakePaymentForTranslatedYoutubeBookResponse implements Serializable {
    private PaymentForTranslatedYoutubeBook payment;
    private TokenTransaction transaction;
}
