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
public class NextVideo implements Serializable {
    private Long startChapter;
    private Long endChapter;
    private Long priceCents;
    private Long priceTokens;
}
