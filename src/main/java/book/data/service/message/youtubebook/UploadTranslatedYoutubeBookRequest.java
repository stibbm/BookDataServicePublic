package book.data.service.message.youtubebook;

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
public class UploadTranslatedYoutubeBookRequest implements Serializable {
    private String bookNumber;
    private String startChapterNumber;
    private String endChapterNumber;
    private VideoData videoData;
}
