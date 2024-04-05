package book.data.service.message.chapter;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetChapterByBookNameAndChapterNumberRequest implements Serializable {
    private String bookName;
    private String chapterNumber;
}
