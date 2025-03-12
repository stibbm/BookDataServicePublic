package book.data.service.constants.videodetailstemplates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoDetailsTemplate implements Serializable {
    private String titleTemplate;
    private String descriptionTemplate;
    private List<String> tags;
    private String playListId;
}
