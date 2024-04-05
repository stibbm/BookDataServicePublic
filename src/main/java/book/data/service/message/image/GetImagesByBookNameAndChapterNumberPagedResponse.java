package book.data.service.message.image;

import book.data.service.model.image.Image;
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
public class GetImagesByBookNameAndChapterNumberPagedResponse implements Serializable {
    private List<Image> imageList;
}
