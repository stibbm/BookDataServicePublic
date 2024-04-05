package book.data.service.message.chapter;

import book.data.service.model.chapter.Chapter;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateChapterResponse implements Serializable {
    private Chapter chapter;
}
