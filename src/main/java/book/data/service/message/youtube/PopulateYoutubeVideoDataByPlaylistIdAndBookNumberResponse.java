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
public class PopulateYoutubeVideoDataByPlaylistIdAndBookNumberResponse
    implements Serializable {
    private List<YouTubeVideo> youtubeVideoList;
    private String playlistId;
}
