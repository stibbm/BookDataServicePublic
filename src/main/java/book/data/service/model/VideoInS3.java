package book.data.service.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoInS3 implements Serializable {
    private String bookNumber;
    private String startChapterNumber;
    private String endChapterNumber;
    private VideoData videoData;
}
