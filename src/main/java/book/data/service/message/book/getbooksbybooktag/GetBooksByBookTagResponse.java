package book.data.service.message.book.getbooksbybooktag;

import book.data.service.sqlmodel.book.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBooksByBookTagResponse implements Serializable {
    private List<Book> bookList;
}
