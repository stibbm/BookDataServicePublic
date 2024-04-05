package book.data.service.message.book;

import book.data.service.model.Book;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBookByBookNameResponse implements Serializable {
    private Book book;
}
