package book.data.service.message.chapter;

import book.data.service.sqlmodel.chapter.Chapter;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetChapterByBookNameAndChapterNumberResponse implements Serializable {
    private Chapter chapter;
}
