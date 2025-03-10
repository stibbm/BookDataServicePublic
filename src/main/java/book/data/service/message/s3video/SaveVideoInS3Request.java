package book.data.service.message.s3video;

import book.data.service.model.VideoData;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveVideoInS3Request implements Serializable {
    private String bookNumber;
    private String startChapterNumber;
    private String endChapter;
    private VideoData videoData;
}
