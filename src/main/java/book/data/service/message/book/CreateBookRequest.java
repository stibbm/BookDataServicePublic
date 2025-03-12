package book.data.service.message.book;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest implements Serializable {
    private String bookName;
    private String bookDescription;
    private String bookLanguage;
    private String bookViews;
    private Set<String> bookTags;
    private byte[] bookThumbnailImageBytes;
    private String fileType;
}
