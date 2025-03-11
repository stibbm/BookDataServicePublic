package book.data.service.youtube;

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
public class CreateYoutubeVideosFromPlaylistResponse implements Serializable {
    private List<YouTubeVideo> youtubeVideoList;
}
