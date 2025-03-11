package book.data.service.message.youtube;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopulateYoutubeVideoDataByPlaylistIdAndBookNumberRequest {
    private String bookNumber;
    private String playListId;
}
