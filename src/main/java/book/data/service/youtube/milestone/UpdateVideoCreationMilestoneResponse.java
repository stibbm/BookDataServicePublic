package book.data.service.youtube.milestone;
import book.data.service.sqlmodel.payment.PaymentForTranslatedYoutubeBook;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVideoCreationMilestoneResponse implements Serializable {
    private PaymentForTranslatedYoutubeBook paymentForTranslatedYoutubeBook;
}
