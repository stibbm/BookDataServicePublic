package book.data.service.message.book;

import java.io.Serializable;

import book.data.service.sqlmodel.book.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookResponse implements Serializable {
    private Book book;
}
