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
public class CreateImageRequest implements Serializable {
    private String bookName;
    private String chapterNumber;
    private String imageNumber;
    private String s3Key;
    private String s3Bucket;
    private String relativeImageUrl;
}
