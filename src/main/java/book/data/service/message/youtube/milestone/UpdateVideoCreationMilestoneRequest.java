package book.data.service.youtube.milestone;

import book.data.service.model.VideoCreationMilestone;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVideoCreationMilestoneRequest implements Serializable {
    private VideoCreationMilestone videoCreationMilestone;
    private String bookNumber;
    private String endChapterNumber;
    private String startChapterNumber;

}
