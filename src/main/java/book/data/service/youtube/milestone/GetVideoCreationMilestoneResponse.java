package book.data.service.youtube.milestone;

import book.data.service.model.VideoCreationMilestone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetVideoCreationMilestoneResponse implements Serializable {
    private Long bookNumber;
    private Long startChapterNumber;
    private Long endChapterNumber;
    private VideoCreationMilestone videoCreationMilestone;
}
