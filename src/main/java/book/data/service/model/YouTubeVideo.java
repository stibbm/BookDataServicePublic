package book.data.service.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YouTubeVideo implements Serializable {
    private String videoId;
    private String title;
    private String description;
    private String videoUrl;
    private String privacyStatus;
    private List<String> tags;
}