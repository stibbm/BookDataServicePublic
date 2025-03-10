package book.data.service.message.audio;

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
public class GetAudiosByBookNameAndChapterNumberRequest implements Serializable {
    private String bookName;
    private String chapterNumber;

    public void validate() {
        Validate.notEmpty(bookName, "book name is required");
        Validate.notEmpty(chapterNumber, "chapterNumber is required");
        Long.parseLong(chapterNumber);
    }
}
