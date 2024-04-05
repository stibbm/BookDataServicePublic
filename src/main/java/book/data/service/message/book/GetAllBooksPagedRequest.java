package book.data.service.message.book;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllBooksPagedRequest implements Serializable {
    private String pageNumber;
    private String pageSize;
}
