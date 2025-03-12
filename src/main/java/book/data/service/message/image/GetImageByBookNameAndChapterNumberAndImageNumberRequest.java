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
public class GetImageByBookNameAndChapterNumberAndImageNumberRequest implements Serializable {
    private String bookName;
    private String chapterNumber;
    private String imageNumber;

    public void validate() {
        Validate.notNull(bookName, "bookName is required");
        Validate.notNull(chapterNumber, "chapterNumber is required");
        Validate.notNull(imageNumber, "imageNumber is required");
    }
}
