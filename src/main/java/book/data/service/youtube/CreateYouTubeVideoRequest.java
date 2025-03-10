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
public class CreateYouTubeVideoRequest implements Serializable {
    private String youtubeVideoId;
    private String bookNumber;
    private String startChapterNumber;
    private String endChapterNumber;
}
