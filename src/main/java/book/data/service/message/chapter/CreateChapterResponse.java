package book.data.service.message.chapter;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import matt.book.data.service.sqlmodel.chapter.Chapter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateChapterResponse implements Serializable {
    private Chapter chapter;
}
