package book.data.service.message.chapter.chapterheader;

import book.data.service.model.ChapterHeader;
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
public class GetChapterHeadersByBookNumberResponse implements Serializable {
    private List<ChapterHeader> chapterHeaderList;
}
