package book.data.service.message.bookview;

import book.data.service.sqlmodel.bookview.BookView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookViewResponse implements Serializable {
    private BookView bookView;
}
