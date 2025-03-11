package book.data.service.message.youtube;

import book.data.service.sqlmodel.youtubevideo.YouTubeVideo;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateYoutubeVideoUploadResponse implements Serializable {
    private YouTubeVideo youtubeVideoSql;
    private book.data.service.model.YouTubeVideo youtubeApiYoutubeVideo;
}
