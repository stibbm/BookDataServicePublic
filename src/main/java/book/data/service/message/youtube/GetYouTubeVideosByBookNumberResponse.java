package book.data.service.message.youtube;

import book.data.service.sqlmodel.youtubevideo.YouTubeVideo;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetYouTubeVideosByBookNumberResponse implements Serializable {
    private List<YouTubeVideo> youTubeVideoList;
}
