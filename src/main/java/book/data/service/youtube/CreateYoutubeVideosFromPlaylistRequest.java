package book.data.service.youtube;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateYoutubeVideosFromPlaylistRequest implements Serializable {
    private String playlistId;
    private String bookId;
}
