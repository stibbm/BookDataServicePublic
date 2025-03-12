package book.data.service.message.bookview;

import book.data.service.sqlmodel.bookview.BookView;
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
public class GetBookViewsByBookNumberResponse implements Serializable {
    private List<BookView> bookViewList;
}
