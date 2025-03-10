package book.data.service.message.tokentransaction;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MakePaymentForTranslatedYoutubeBookRequest implements Serializable {
    private String dedupeToken;
    private String bookNumber;
    private String startChapter;
    private String endChapter;
    private String tokenAmount;
}
