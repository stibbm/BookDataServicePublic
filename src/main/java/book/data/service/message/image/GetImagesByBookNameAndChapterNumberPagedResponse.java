package book.data.service.message.image;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import matt.book.data.service.sqlmodel.image.Image;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetImagesByBookNameAndChapterNumberPagedResponse implements Serializable {
    private List<Image> imageList;
}
