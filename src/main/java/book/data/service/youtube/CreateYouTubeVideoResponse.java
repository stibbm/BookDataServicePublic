package book.data.service.youtube;

import book.data.service.model.YouTubeVideo;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateYouTubeVideoResponse implements Serializable {
    private YouTubeVideo youTubeVideo;
}
