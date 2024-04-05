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
public class GetChaptersByBookNamePagedRequest implements Serializable {
    private String bookName;
    private String pageNumber;
    private String pageSize;
}
