package book.data.service.model;

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
public class YoutubeVideoInfo implements Serializable {
    private String videoId;
    private String title;
    private String description;
    private List<String> tagList;
    private String categoryId;
}
