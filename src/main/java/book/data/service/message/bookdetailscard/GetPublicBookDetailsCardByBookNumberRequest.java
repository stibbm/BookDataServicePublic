package book.data.service.message.bookdetailscard;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPublicBookDetailsCardByBookNumberRequest implements Serializable {
    private String bookNumber;
}
