package book.data.service.message.book.searchbooksbybooktags;

import book.data.service.sqlmodel.book.Book;
import book.data.service.sqlmodel.tag.TagFilterType;
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
public class SearchBooksByBookTagsResponse implements Serializable {
    private List<Book> bookList;
    private TagFilterType tagFilterType;

}
