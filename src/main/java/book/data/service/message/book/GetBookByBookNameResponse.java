package book.data.service.message.book;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import matt.book.data.service.sqlmodel.book.Book;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBookByBookNameResponse implements Serializable {
    private Book book;
}
