package book.data.service.message.bookview;

import java.io.Serializable;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookViewRequest implements Serializable {
    private String bookNumber;
}
