package book.data.service.message.video;


import book.data.service.sqlmodel.video.Video;
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
public class GetVideosByBookNameAndChapterNumberResponse implements Serializable {
    private List<Video> videoList;
}
