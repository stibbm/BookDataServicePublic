package book.data.service.message.image;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetImagesByBookNameAndChapterNumberPagedRequest implements Serializable {
    private String bookName;
    private String chapterNumber;
    private String pageNumber;
    private String pageSize;
}
