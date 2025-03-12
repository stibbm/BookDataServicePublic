package book.data.service.message.populate;

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
public class PopulateVideoDataForAllSavedPlaylistsResponse implements
    Serializable {
    private Boolean done;
    private List<List<YouTubeVideo>> youtubePlaylistList;
}
