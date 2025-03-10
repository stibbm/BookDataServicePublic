package book.data.service.message.textblock;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTextBlockByBookNameAndChapterNumberRequest implements Serializable {
    private String bookName;
    private String chapterNumber;
}
