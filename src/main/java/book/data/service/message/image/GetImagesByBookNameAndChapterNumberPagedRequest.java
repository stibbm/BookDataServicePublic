package book.data.service.message.image;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.Validate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetImagesByBookNameAndChapterNumberPagedRequest implements Serializable {
    private String bookName;
    private String chapterNumber;
    private String pageNumber;
    private String pageSize;

    public void validate() {
        Validate.notNull(bookName, "bookName required");
        Validate.notNull(chapterNumber, "chapterNumber required");
        Validate.notNull(pageNumber, "pageNumber required");
        Validate.notNull(pageSize, "pageSize required");
    }
}
