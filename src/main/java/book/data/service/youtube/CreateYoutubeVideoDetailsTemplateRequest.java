package book.data.service.youtube;

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
public class CreateYoutubeVideoDetailsTemplateRequest implements Serializable {
    private String bookNumber;
    private String titleTemplate;
    private String descriptionTemplate;
    private List<String> videoTagList;
}
