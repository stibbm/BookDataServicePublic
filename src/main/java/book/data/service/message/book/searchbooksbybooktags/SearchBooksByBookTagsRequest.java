package book.data.service.message.book.searchbooksbybooktags;

import book.data.service.sqlmodel.tag.TagFilterType;
import com.google.auto.value.AutoValue.Builder;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchBooksByBookTagsRequest implements Serializable {
    private List<String> bookTags;
    private TagFilterType tagFilterType;;
}
