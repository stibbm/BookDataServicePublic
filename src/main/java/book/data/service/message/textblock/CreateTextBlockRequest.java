package book.data.service.message.textblock;

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
public class CreateTextBlockRequest implements Serializable {
    private String bookName;
    private String chapterNumber;
    private String textBlockText;

    public void validate() {
        Validate.notEmpty(bookName, "bookName is required");
        Validate.notEmpty(chapterNumber, "chapterNumber is required");
        Validate.notEmpty(textBlockText, "textBlockText is required");
    }
}
