package book.data.service.youtube.milestone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetVideoCreationMilestoneRequest implements Serializable {
    private String bookNumber;
    private String startChapterNumber;
    private String endChapterNumber;
}
