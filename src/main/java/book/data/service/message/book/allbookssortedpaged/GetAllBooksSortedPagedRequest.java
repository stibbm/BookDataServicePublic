package book.data.service.message.book.allbookssortedpaged;

import book.data.service.constants.BookSortTypes;
import book.data.service.exception.book.InvalidBookSortTypeException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllBooksSortedPagedRequest implements Serializable {
    private String sortType;
    private String pageNumber;
    private String pageSize;

    public void validateSortTypeAndPagingValues() {
        if (!sortType.equalsIgnoreCase(BookSortTypes.BOOK_VIEWS)
                && !sortType.equalsIgnoreCase(BookSortTypes.CREATION_TIME)
                && (!sortType.equalsIgnoreCase(BookSortTypes.BOOK_NAME)
        )) {
            throw new InvalidBookSortTypeException();
        }
        int pageNumberInt = Integer.parseInt(pageNumber);
        int pageSizeInt = Integer.parseInt(pageSize);
    }
}
