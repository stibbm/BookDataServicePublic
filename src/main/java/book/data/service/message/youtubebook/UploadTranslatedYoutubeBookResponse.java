package book.data.service.message.youtubebook;

import book.data.service.model.YouTubeVideo;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadTranslatedYoutubeBookResponse implements Serializable {
    private YouTubeVideo youTubeVideo;
}
