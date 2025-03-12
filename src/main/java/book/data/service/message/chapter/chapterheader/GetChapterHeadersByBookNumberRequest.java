package book.data.service.message.chapter.chapterheader;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetChapterHeadersByBookNumberRequest implements Serializable {
    private String bookNumber;
}
