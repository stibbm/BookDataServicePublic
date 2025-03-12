package book.data.service.message.book.getbookbybooknumber;

import book.data.service.sqlmodel.book.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBookByBookNumberResponse implements Serializable {
    private Book book;
}
