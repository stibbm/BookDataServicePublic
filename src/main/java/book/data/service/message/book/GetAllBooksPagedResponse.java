package book.data.service.message.book;

import java.io.Serializable;
import java.util.List;

import book.data.service.sqlmodel.book.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllBooksPagedResponse implements Serializable {
    private List<Book> bookList;
    private int pageNumber;
    private int pageSize;
}
