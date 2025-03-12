package book.data.service.message.chapter;

import book.data.service.sqlmodel.chapter.Chapter;
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
public class GetChaptersByBookNamePagedResponse implements Serializable {
    private List<Chapter> chapterList;
    private int pageNumber;
    private int pageSize;
}
