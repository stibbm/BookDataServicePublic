package book.data.service.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterHeader implements Serializable {
    private String bookName;
    private Long chapterNumber;
    private String chapterName;
    private Long chapterViews;
    private String createdBy;
    private Integer imagesCount;
    private String chapterType;
    private Integer charCount;
}
