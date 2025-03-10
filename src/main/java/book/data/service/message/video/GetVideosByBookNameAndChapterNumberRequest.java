package book.data.service.message.video;

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
public class GetVideosByBookNameAndChapterNumberRequest implements Serializable {
    private String bookName;
    private String chapterNumber;

    public void validate() {
        Validate.notEmpty(bookName, "bookname is required");
        Validate.notEmpty(chapterNumber, "chapterNumber is required");
    }

}
