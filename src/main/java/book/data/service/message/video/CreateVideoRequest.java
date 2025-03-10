package book.data.service.message.video;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVideoRequest implements Serializable {
    private String bookName;
    private String chapterNumber;
    private String videoNumber;
    private byte[] videoBytes;
    private String fileType;

    public void validate() {
        Validate.notEmpty(bookName, "bookname is required");
        Validate.notEmpty(chapterNumber, "chapternumber is required");
        Validate.notEmpty(videoNumber, "videonumber is required");
        Validate.notNull(videoBytes, "videobytes is required");
        Validate.notNull(fileType, "fileType is required");
        Long.parseLong(chapterNumber);
        Long.parseLong(videoNumber);
    }
}
