package book.data.service.message.bookview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBookViewsByBookNumberRequest implements Serializable {
    private String bookNumber;
}
