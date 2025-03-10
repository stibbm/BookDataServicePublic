package book.data.service.message.bookdetailscard;

import book.data.service.model.PublicBookDetailsCard;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPublicBookDetailsCardByBookNumberResponse implements Serializable {
    private PublicBookDetailsCard publicBookDetailsCard;
}
