package book.data.service.message.book.searchbooks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchBooksRequest implements Serializable {
    private String searchText;
    private SearchTermFilterType searchTermFilterType;
}
