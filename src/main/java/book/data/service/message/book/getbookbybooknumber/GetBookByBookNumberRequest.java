package book.data.service.message.book.getbookbybooknumber;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBookByBookNumberRequest implements Serializable {
    private Long bookNumber;
}
