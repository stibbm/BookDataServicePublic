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
public class CreateAudioRequest implements Serializable {
    private String bookName;
    private String chapterNumber;
    private String audioNumber;
    private byte[] fileBytes;
    private String fileType;

    public void validate() {
        Validate.notEmpty(bookName, "bookName is required");
        Validate.notEmpty(chapterNumber, "chapterNumber is required");
        Validate.notEmpty(audioNumber, "audioNumber is required");
        Validate.notNull(fileBytes, "fileBytes is required");
        Validate.notNull(fileType, "fileType is required");
        Long.parseLong(chapterNumber);
        Long.parseLong(audioNumber);
    }
}
